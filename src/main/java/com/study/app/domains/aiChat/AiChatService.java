package com.study.app.domains.aiChat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tools.jackson.databind.ObjectMapper;

@Service
public class AiChatService {

	@Autowired
	private VectorStore vectorStore;

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
		
		aiDao.insertMessage(new AiMessagesDTO(0L, chat_seq, role, content, null, null));

		Map<String, Object> aiResult = new HashMap<>();
		aiResult.put("chat_seq", chat_seq);

		List<Document> similarDocs = vectorStore.similaritySearch(
				SearchRequest
				.builder()
				.query(content)
				.topK(3)
				.build());
		
		// 답변을 찾은 청크 고유 ID(UUID) 뽑기
		List<String> refChunkIds = similarDocs.stream()
				.map(Document::getId)
				.collect(Collectors.toList());
		System.out.println(refChunkIds);
		
		// ObjectMapper를 사용해서 뽑아온 리스트를 [uuid-1, uuid-2] 형태의 JSON 배열로 직렬화
		String dbRefChunkValue = null;
		try {
			dbRefChunkValue = objectMapper.writeValueAsString(refChunkIds);
			System.out.println(dbRefChunkValue);
		}catch(Exception e) {
			e.printStackTrace();
			dbRefChunkValue = "[]"; // 오류 발생 시 빈 배열 주입
			System.out.println("변환 작업 중 오류 발생");
		}
		
		
		double maxScore = 0.0;
		if(!similarDocs.isEmpty()) {
			maxScore = similarDocs.get(0).getScore();
			System.out.println("최고 유사도 점수 : " + maxScore);
		}
		// 인사 문의 : 0.72
		// 연차 발생 규정 : 0.70
		// 복리후생 : 0.70
		// 색상 팔레트 : 0.66
		// 개발시 주의사항 : 0.69
		// 사과는 무슨 색 : 0.52
		// 공룡 이름 : 0.41
		// 어떤 화장품이 좋아? : 0.46
		
		double scoreThreshold = 0.60;
		if(similarDocs.isEmpty() || maxScore < scoreThreshold) {

			String failmsg = "사내 데이터베이스에서 '" + content + "'와(과) 관련된 규정이나 가이드를 찾지 못했습니다. 😢\n"
					+ "정확한 안내를 위해 해당 질문은 관리자에게 문의해 주세요.";
			
			aiDao.insertMessage(new AiMessagesDTO(0L, chat_seq, "AI", failmsg, null, null));
			
			aiResult.put("aiAnswer", failmsg);
			return aiResult;

		}

		String context = similarDocs.stream().map(Document::getFormattedContent)
				.collect(Collectors.joining("\n\n"));

		String systemPrompt = "당신은 사내 그룹웨어 시스템의 스마트 업무 지원 AI 비서입니다."
				+ "임직원이 안심하고 업무 가이드를 얻을 수 있도록, 아래 제공된 [사내 문서 데이터]만을 바탕으로 정중하고 객관적으로 답변해야 합니다."
				+ ""
				+ "[업무 가이드라인 및 주의사항]"
				+ "1. 유저의 질문에 대해 제공된 [사내 문서 데이터]의 '대제목이나 목차'만 나열하지 말고, 반드시 그 아래에 있는 **상세 본문 내용(구체적인 행동 요령, 준비물, 절차 등)을 빠짐없이 요약·정리하여 답변**하세요."
//				+ "1. 철저하게 제공된 [사내 문서 데이터] 내의 공식 규정, 절차, 비용 한도 및 복리후생 내용에 기반하여 답변하세요."
				+ "2. 특히 데이터가 마크다운 표(Table) 형식('|항목|한도|')으로 제공될 경우, 표의 행과 열을 정확하게 매칭하여 금액 및 조건 수치를 누락없이 임직원에게 안내하세요."
				+ "3. 데이터 내에 존재하는 구체적인 단어나 기준(예:신분증, 복장 종류, 제출 기한 등)을 생략하거나 숨기지 말고 임직원에게 명확하게 매뉴얼 형태로 풀어서 안내하세요."
				+ "3. 문서 내용으로 확답을 줄 수 없거나 정보가 완전히 부족한 경우에만 '해당 사항에 대한 공식 규정 문서가 확인되지 않습니다' 라고 정중히 안내하세요. 데이터에 존재하는 수치는 절대 숨기거나 모른다고 하지 마세요."
				+ "4. 임직원을 대하는 격식있고 명확한 비즈니스 어조(한글 존댓말 씁니다.)를 유지하세요."
				+ "5. 답변을 출력할 때도 임직원이 보기 편하도록 항목별 줄바꿈과 마크다운 포맷을 활용하여 깔끔하게 정돈된 형태로 출력하세요."
				+ ""
				+ "[사내 문서 데이터]"
				+ "%s".formatted(context);

		Prompt prompt = new Prompt(List.of(
				new SystemMessage(systemPrompt),
				new UserMessage(content)));

		ChatResponse response = chatModel.call(prompt);
		String aiAnswer = response.getResult().getOutput().getText();
		System.out.println(dbRefChunkValue);
		
		aiDao.insertMessage(new AiMessagesDTO(0L, chat_seq, "AI", aiAnswer, dbRefChunkValue, null));
		aiResult.put("aiAnswer", aiAnswer);
		return aiResult;
	}


}
