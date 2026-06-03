package com.study.app.domains.aiChat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import jakarta.annotation.PostConstruct;
import tools.jackson.databind.ObjectMapper;

@Service
public class AiChatService {

	@Value("${python.api.url}")
	private String pythonApiUrl;

	private RestClient restClient;

	@PostConstruct
	public void init() {
		restClient = RestClient.builder()
				.baseUrl(pythonApiUrl)
				.build();
	}

	@Autowired
	@Qualifier("googleGenAiChatModel")
	private ChatModel chatModel;

	@Autowired
	private AiChatDAO aiDao;

	@Autowired
	private ObjectMapper objectMapper;

	@Transactional
	public Map<String, Object> getRagResponse(String loginId, Long chat_seq, String role, String content) {

		// 첫 문의일 경우 AI_CHAT title 뽑기
		if(chat_seq == 0) {
			String rawTitle =  content.trim();
			String chatTitle = rawTitle.length() > 20 ? rawTitle.substring(0, 20) + "..." : rawTitle;

			AiChatDTO aiChatDTO = new AiChatDTO();
			aiChatDTO.setUsers_id(loginId);
			aiChatDTO.setTitle(chatTitle);

			aiDao.insertAiChat(aiChatDTO);
			chat_seq = aiChatDTO.getChat_seq();
		}else {
			aiDao.updateAiChatUpdated_at(chat_seq);
		}

		aiDao.insertMessage(new AiMessagesDTO(0L, chat_seq, role, content, null, null,null));

		Map<String, Object> aiResult = new HashMap<>();
		aiResult.put("chat_seq", chat_seq);



		Map<String, Object> requestBody = new HashMap<>();

		requestBody.put("query", content);
		requestBody.put("limit", 10);

		List<SearchResultDTO> similarDocs =
				restClient.post()
				.uri("/search")
				.body(requestBody)
				.retrieve()
				.body(new ParameterizedTypeReference<List<SearchResultDTO>>() {});

		double maxScore = similarDocs.stream()
				.map(SearchResultDTO::getScore)
				.filter(Objects::nonNull)
				.max(Double::compareTo)
				.orElse(0.0);

		System.out.println("=================");
		System.out.println("최고 유사도 : " + maxScore);
		System.out.println("=================");
		// 연차 사용 : 0.7673025
		// 이니셔티브별 현황 : 0.8592471
		// 출근 전 준비할 사항 : 0.8780443

		double threshold = 0.53;
		
		List<SearchResultDTO> filteredDocs = similarDocs.stream()
					.filter(doc -> doc.getScore() != null)
					.filter(doc -> doc.getScore() >= threshold)
					.limit(5)
					.collect(Collectors.toList());
		
		System.out.println("=================");
		System.out.println("필터링 후 청크 수 : " + filteredDocs.size());
		filteredDocs.forEach(doc -> {
			System.out.println("필터링 된 각 score : " + doc.getScore());
			System.out.println("필터링된 반환 내용 : " + doc.getText());
			System.out.println("=================");
		});

		if (filteredDocs.isEmpty()) {

			aiResult.put("chat_seq", chat_seq);

			String aiAnswer = "사내 데이터베이스에서 '" + content + "'와(과) 관련된 규정이나 가이드를 찾지 못했습니다. 😢\n"
							+ "정확한 안내를 위해 해당 질문은 관리자에게 문의해 주세요.";


			aiDao.insertMessage(new AiMessagesDTO(0L,chat_seq,"AI",
					aiAnswer,null,null,null));

			aiResult.put("aiAnswer", aiAnswer);

			return aiResult;
		}

		List<Long> refChunkIds = filteredDocs.stream()
				.map(SearchResultDTO::getChunk_seq)
				.toList();

		String dbRefChunkValue;
		try {
			dbRefChunkValue =
					objectMapper.writeValueAsString(refChunkIds);
		} catch (Exception e) {
			dbRefChunkValue = "[]";
		}

		String context = filteredDocs.stream()
				.map(SearchResultDTO::getText)
				.collect(Collectors.joining("\n\n"));

		String systemPrompt = "당신은 사내 그룹웨어 시스템의 스마트 업무 지원 AI 비서입니다."
				+ "임직원이 안심하고 업무 가이드를 얻을 수 있도록, 아래 제공된 [사내 문서 데이터]만을 바탕으로 정중하고 객관적으로 답변해야 합니다."
				+ ""
				+ "[업무 가이드라인 및 주의사항]"
				+ "1. 유저의 질문에 대해 제공된 [사내 문서 데이터]의 '대제목이나 목차'만 나열하지 말고, 반드시 그 아래에 있는 **상세 본문 내용(구체적인 행동 요령, 준비물, 절차 등)을 빠짐없이 요약·정리하여 답변**하세요."
				//				+ "1. 철저하게 제공된 [사내 문서 데이터] 내의 공식 규정, 절차, 비용 한도 및 복리후생 내용에 기반하여 답변하세요."
				+ "2. 특히 데이터가 마크다운 표(Table) 형식('|항목|한도|')으로 제공될 경우, 표의 행과 열을 정확하게 매칭하여 금액 및 조건 수치를 누락없이 임직원에게 안내하세요."
				+ "[업무 가이드라인 및 주의사항]\r\n"
				+ "3. 유저의 질문과 직접적인 연관이 있는 [사내 문서 데이터] 내용만을 바탕으로 답변하세요. 제목이나 목차만 존재하는 청크는 무시하고, 구체적인 행동 요령과 절차가 적힌 본문 내용을 우선하여 요약·정리하세요.\r\n"
				+ "4. 제공된 데이터 중 유저의 핵심 질문(\"출근 전 준비사항\")과 맥락이 맞지 않는 노이즈 데이터(예: 출퇴근 방법 등)가 섞여 있다면, 무리하게 답변에 포함하지 말고 과감히 제외하십시오."
				+ "5. 데이터 내에 존재하는 구체적인 단어나 기준(예:신분증, 복장 종류, 제출 기한 등)을 생략하거나 숨기지 말고 임직원에게 명확하게 매뉴얼 형태로 풀어서 안내하세요."
				+ "6. 문서 내용으로 확답을 줄 수 없거나 정보가 완전히 부족한 경우에만 '해당 사항에 대한 공식 규정 문서가 확인되지 않습니다' 라고 정중히 안내하세요. 데이터에 존재하는 수치는 절대 숨기거나 모른다고 하지 마세요."
				+ "7. 임직원을 대하는 격식있고 명확한 비즈니스 어조(한글 존댓말 씁니다.)를 유지하세요."
				+ "8. 답변을 출력할 때도 임직원이 보기 편하도록 항목별 줄바꿈과 마크다운 포맷을 활용하여 깔끔하게 정돈된 형태로 출력하세요."
				+ ""
				+ "[사내 문서 데이터]"
				+ "%s".formatted(context);

		Prompt prompt = new Prompt(List.of(
				new SystemMessage(systemPrompt),
				new UserMessage(content)));

		ChatResponse response = chatModel.call(prompt);
		String aiAnswer = response.getResult().getOutput().getText();
		System.out.println(dbRefChunkValue);

		aiDao.insertMessage(new AiMessagesDTO(0L, chat_seq, "AI", aiAnswer, dbRefChunkValue, null, null));
		aiResult.put("aiAnswer", aiAnswer);
		return aiResult;
	}

	public List<AiChatDTO> sideChatTitleList(String loginId) {
		return aiDao.sideChatTitleList(loginId);
	}

	public List<AiMessagesDTO> detailChat(Long chat_seq) {
		return aiDao.detailChat(chat_seq);
	}
	
	public void insertQuestion(String loginId, 
			AiUnansweredQuestionsDTO dto) {
		Map<String, Object> params = new HashMap<>();
		
		params.put("users_id", loginId);
		params.put("dto", dto);
		
		aiDao.insertQuestion(params);
	}

	
}
