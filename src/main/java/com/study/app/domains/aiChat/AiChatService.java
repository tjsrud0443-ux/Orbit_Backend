package com.study.app.domains.aiChat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
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

import com.study.app.domains.meetingMinutes.MeetingMinutesDAO;
import com.study.app.domains.meetingMinutes.MeetingMinutesDTO;

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
	private RagDAO ragDao;
	
	@Autowired
	private MeetingMinutesDAO meetingMinutesDao;
	
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
		System.out.println("질문 사항 : " + content);
		System.out.println("최고 유사도 : " + maxScore);
		System.out.println("=================");

		List<SearchResultDTO> filteredDocs = similarDocs.stream()
				.limit(5)
				.collect(Collectors.toList());

		System.out.println("=================");
		System.out.println("청크 수 : " + filteredDocs.size());
		filteredDocs.forEach(doc -> {
			System.out.println("각 score : " + doc.getScore());
			System.out.println("반환 내용 : " + doc.getText());
			System.out.println("=================");
		});
		
		List<Long> meetingSeqs = meetingMinutesDao.meetingSeqs(loginId);
		
		List<Long> ragDocSeqs = filteredDocs.stream()
				.sorted(
						Comparator.comparing(
								SearchResultDTO::getScore
								).reversed()
						)
				.map(SearchResultDTO::getRag_doc_seq)
				.limit(3)
				.distinct()
				.toList();

		List<RagDocumentsDTO> resultSources = ragDao.sourcesByRagDocSeqs(ragDocSeqs);
		resultSources = resultSources.stream().filter(doc -> "DOCUMENTS".equals(doc.getSource_type())).toList();

		if (filteredDocs.isEmpty() || maxScore < 0.20) {

			System.out.println("검색 실패");
			System.out.println("maxScore = " + maxScore);
			System.out.println("filteredDocs.size = " + filteredDocs.size());

			aiResult.put("chat_seq", chat_seq);
			String aiAnswer = "사내 데이터베이스에서 '" + content + "'와(과) 관련된 규정이나 가이드를 찾지 못했습니다. 😢\n"
					+ "정확한 안내를 위해 해당 질문은 관리자에게 문의해 주세요.";

			aiDao.insertMessage(new AiMessagesDTO(0L,chat_seq,"AI",
					aiAnswer,null,null,null));

			aiResult.put("aiAnswer", aiAnswer);
			aiResult.put("resultSources", Collections.emptyList());

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
		String systemPrompt;

		if(context.contains("회의명 :")) {
			systemPrompt ="당신은 사내 그룹웨어 시스템의 스마트 업무 지원 AI 비서이자, 프로페셔널한 회의록 요약 전문가입니다."
		            + "제공된 [회의록 데이터]만을 바탕으로, 임직원들이 회의 핵심 내용을 한눈에 파악할 수 있도록 정중하고 객관적으로 답변해야 합니다."
		            + ""
		            + "[사용자 의도 파악 및 유연한 대응 규칙 (필수)]"
		            + "1. **[전체 요약 요청 시]**: 사용자가 회의록의 '전체 요약'을 원하거나 특정 항목을 지정하지 않은 경우, 아래의 **[지정된 출력 형식]** 4가지를 모두 사용하여 답변하세요."
		            + "2. **[특정 항목 요청 시]**: 사용자가 특정 내용만 딱 집어서 질문한 경우(예: \"결정 사항만 알려줘\", \"할 일이 뭐야?\", \"어떤 내용이 논의됐어?\"), 요구하지 않은 다른 분류는 과감히 생략하고 **사용자가 요청한 핵심 항목의 내용과 핵심 개요의 내용을 추출하여 답변**하세요. (예: 할 일만 물었다면 '*회의 개요와 *할 일' 내용만 출력)"
		            + ""
		            + "[회의록 요약 가이드라인 및 주의사항]"
		            + "1. **[핵심 중심 요약]**: 회의 중 발생한 단순 인사말, 잡담, 지엽적인 의견 대립 과정 등의 노이즈는 과감히 제외하고, '최종 결론'과 '합의된 사항'을 중심으로 요약하세요."
		            + "2. **[구체성 유지]**: 주요 논의 내용이나 결정 사항을 작성할 때, 대화의 맥락을 알 수 있도록 구체적인 배경이나 이유를 포함하여 서술하세요. 단어만 나열하는 무성의한 요약은 금지합니다."
		            + "3. **[담당자 및 기한 명시]**: 할 일을 작성할 때는 회의록에 언급된 **담당자 이름(또는 부서)**과 **완료 목표 기한(Due Date)**을 데이터에서 찾아 명확하게 매칭하여 기록하세요."
		            + "4. **[엄격한 사실 근거]**: 데이터에 존재하지 않는 내용이나 회의 중 확정되지 않은 추측성 정보, 일반 상식을 답변에 절대 추가하지 마십시오. 오직 제공된 [회의록 데이터]의 내용으로만 답변해야 합니다."
		            + "5. **[예외 처리]**: 제공된 [회의록 데이터]가 유저가 요청한 질문이나 특정 회의 내용과 전혀 관련이 없거나, 핵심 정보가 부족한 경우 추측하여 답변하지 마십시오."
		            + " 이 경우에는 반드시 유저의 질문을 포함해 '사내 데이터베이스에서 (유저질문) 와(과) 관련된 회의록 규정이나 내용을 찾지 못했습니다. 😢'라고 답하십시오."
		            + "6. 임직원을 대하는 격식있고 명확한 비즈니스 어조(한글 존댓말)를 유지하세요. 답변의 마지막에는 항상 '추가로 궁금하신 사항이 있으시면 언제든 말씀해 주시길 바랍니다.'라는 정중한 맺음말을 붙이십시오."
		            + "7. **[출력 포맷팅]**: 임직원이 보기 편하도록 항목별 줄바꿈을 적극 활용하고, 소분류나 상세 내용에는 -(하이픈)을 활용하여 가독성 높게 출력하세요."
		            + ""
		            + "[지정된 출력 형식 (전체 요약용)]"
		            + "1. 회의 개요"
		            + "- 회의명, 일시 등 개요 정보 요약"
		            + "2. 주요 내용"
		            + "- 회의에서 다루어진 핵심 안건과 논의 배경"
		            + "3. 결정 사항"
		            + "- 합의된 최종 결론 및 승인된 사항"
		            + "4. 할 일"
		            + "- 담당자, 구체적인 실행 과제, 완료 기한"
		            + ""
		            + "[회의록 데이터]"
		            + "%s".formatted(context);
		}else {
			systemPrompt = "당신은 사내 그룹웨어 시스템의 스마트 업무 지원 AI 비서입니다."
					+ "임직원이 안심하고 업무 가이드를 얻을 수 있도록, 아래 제공된 [사내 문서 데이터]만을 바탕으로 정중하고 객관적으로 답변해야 합니다."
					+ ""
					+ "[업무 가이드라인 및 주의사항]"
					+ "1. 유저의 질문에 대해 제공된 [사내 문서 데이터]의 '대제목이나 목차'만 나열하지 말고, 반드시 그 아래에 있는 **상세 본문 내용(구체적인 행동 요령, 준비물, 절차 등)을 빠짐없이 요약·정리하여 답변**하세요."
					+ "2. 특히 데이터가 마크다운 표(Table) 형식('|항목|한도|')으로 제공될 경우, 표의 행과 열을 정확하게 매칭하여 금액 및 조건 수치를 누락없이 임직원에게 안내하세요."
					+ "[업무 가이드라인 및 주의사항]\r\n"
					+ "3. 유저의 질문과 직접적인 연관이 있는 [사내 문서 데이터] 내용만을 바탕으로 답변하세요. 제목이나 목차만 존재하는 청크는 무시하고, 구체적인 행동 요령과 절차가 적힌 본문 내용을 우선하여 요약·정리하세요.\r\n"
					+ "4. 제공된 데이터 중 유저의 핵심 질문(\"출근 전 준비사항\")과 맥락이 맞지 않는 노이즈 데이터(예: 출퇴근 방법 등)가 섞여 있다면, 무리하게 답변에 포함하지 말고 과감히 제외하십시오."
					+ "5. 데이터 내에 존재하는 구체적인 단어나 기준(예:신분증, 복장 종류, 제출 기한 등)을 생략하거나 숨기지 말고 임직원에게 명확하게 매뉴얼 형태로 풀어서 안내하세요."
					+ "6. 사용자의 질문에 답하기 위한 핵심 정보가 [사내 문서 데이터]에 존재하지 않으면 절대 추측하거나 일반 상식을 사용하지 마십시오."
					+ " 사용자의 질문과 직접적으로 일치하는 규정, 절차, 정책, 회의 내용이 데이터에 없는 경우에는 반드시 사용자의 질문을 함께 포함해 '사내 데이터베이스에서 (사용자질문) 와(과) 관련된 규정이나 가이드를 찾지 못했습니다. 😢'라고 답하십시오."
					+ " 예를 들어 일반 상식 질문(예: 사과는 무슨 색인가, 대한민국 수도는 어디인가, 오늘 날씨는 어떤가 , 공룡 이름 등)은 사내 문서 데이터와 관련이 없으므로 답변하지 마십시오."
					+ " 검색 결과에 단순히 유사한 단어가 포함되어 있더라도 질문에 대한 근거가 되지 않으면 해당 내용을 사용하지 마십시오."
					+ "7. 임직원을 대하는 격식있고 명확한 비즈니스 어조(한글 존댓말 씁니다.)를 유지하세요."
					+ "답변의 마지막에는 항상 '추가로 궁금하신 사항이 있으시면 언제든 말씀해 주시길 바랍니다.'라는 정중한 맺음말을 붙이십시오."
					+ "8. 답변을 출력할 때도 임직원이 보기 편하도록 항목별 줄바꿈을 활용하고, 대분류에는 숫자를 붙이며, 소분류에는 -(하이픈)을 활용하여 깔끔하게 정돈된 형태로 출력하세요."
					+ "표(Table) 데이터는 사용자의 질문에 직접적으로 필요한 경우에만 활용하십시오."
					+ "사용자가 특정 항목을 요청하지 않은 경우 표 전체를 나열하거나 목록(*) 형태로 재구성하여 출력하지 마십시오."
					+ "9. [사내 문서 데이터]가 사용자의 질문과 주제적으로 일치하지 않으면 답변하지 마십시오."
					+ " 예를 들어 기술 스택 문서가 검색되었는데 사용자가 복리후생을 질문한 경우, 해당 문서를 근거로 답변해서는 안 됩니다."
					+ " 회의록이 검색되었더라도 질문과 관련 없는 회의 내용이면 무시하십시오."
					+ "10. 답변을 생성하기 전에 먼저 [사내 문서 데이터]가 사용자의 질문에 실제로 답할 수 있는 근거를 포함하는지 판단하십시오."
					+ " 질문과 직접 관련된 규정, 절차, 정책, 기술 정보, 회의 내용이 존재하지 않으면 답변을 생성하지 말고 반드시 '사내 데이터베이스에서 (질문) 와(과) 관련된 규정이나 가이드를 찾지 못했습니다. 😢'라고 답하십시오."
					+ " 단순히 일부 단어가 유사하거나 같은 부서 문서가 검색되었다는 이유만으로 답변을 생성해서는 안 됩니다."
					+ ""
					+ "[사내 문서 데이터]"
					+ "%s".formatted(context);
		}
		
		Prompt prompt = new Prompt(List.of(
				new SystemMessage(systemPrompt),
				new UserMessage(content)));

		ChatResponse response = chatModel.call(prompt);
		String aiAnswer = response.getResult().getOutput().getText();

		if(aiAnswer.contains("찾지 못했습니다") || aiAnswer.contains("관련된 규정이나 가이드를 찾지 못했습니다")) {
			aiResult.put("resultSources", Collections.emptyList());
		}else {
			aiResult.put("resultSources", resultSources);
		}
		System.out.println(dbRefChunkValue);

		aiDao.insertMessage(new AiMessagesDTO(0L, chat_seq, "AI", aiAnswer, dbRefChunkValue, null, null));
		aiResult.put("aiAnswer", aiAnswer);
		return aiResult;
	}

	public void createChunk(Long file_seq, Long document_seq, String fileName, String signedUrl, String mimeType) {
		Map<String, Object> body = new HashMap<>();

		body.put("fileName", fileName);
		body.put("signedUrl", signedUrl);
		body.put("mimeType", mimeType);

		ChunkResponseDTO response = 
				restClient.post()
				.uri("/document/chunk")
				.body(body)
				.retrieve()
				.body(ChunkResponseDTO.class);

		RagDocumentsDTO ragDoc = new RagDocumentsDTO();

		ragDoc.setSource_type("DOCUMENTS");
		ragDoc.setSource_seq(document_seq);
		ragDoc.setFile_seq(file_seq);
		ragDoc.setFile_name(fileName);
		ragDoc.setFile_ext(FilenameUtils.getExtension(fileName));
		ragDoc.setRaw_text(response.getRaw_text());
		ragDoc.setExtract_status("DONE");

		ragDao.insertRagDocuments(ragDoc);

		Long ragDocSeq = ragDoc.getRag_doc_seq();
		for(ChunkItemDTO chunk : response.getChunks()) {

			RagChunksDTO dto = new RagChunksDTO();

			dto.setRag_doc_seq(ragDocSeq);
			dto.setChunk_index(chunk.getChunk_index());
			dto.setChunk_text(chunk.getChunk_text());
			dto.setEmbed_status("PENDING");

			ragDao.insertRagChunks(dto);
		} 
		embedChunk(ragDocSeq, fileName);
	}

	public void createMeetingChunk(MeetingMinutesDTO dto) {
		String raw_text = 
				"회의명 : " + dto.getTitle() + "\n\n" +
						"[주요 내용] : " + dto.getMain_content() + "\n\n" +
						"[결정 사항] : " + dto.getDecisions() + " \n\n" + 
						"[할 일] : " + dto.getTodos();

		RagDocumentsDTO ragDoc = new RagDocumentsDTO();

		ragDoc.setSource_type("MEETING_MINUTES");
		ragDoc.setSource_seq(dto.getMinute_seq());
		ragDoc.setFile_seq(null);
		ragDoc.setFile_name(dto.getTitle());
		ragDoc.setFile_ext("MEETING");
		ragDoc.setRaw_text(raw_text);
		ragDoc.setExtract_status("DONE");

		ragDao.insertRagDocuments(ragDoc);


		Long ragDocSeq = ragDoc.getRag_doc_seq();

		Long chunkIndex = 0L;

		if(dto.getMain_content() != null 
				&& !dto.getMain_content().isEmpty()) {
			RagChunksDTO mainChunk = new RagChunksDTO();

			mainChunk.setRag_doc_seq(ragDocSeq);
			mainChunk.setChunk_index(chunkIndex++);
			mainChunk.setChunk_text(
					"회의명 : " + dto.getTitle() + "\n" +
							"회의 일자: " + dto.getMeeting_dt() + 
							"\n\n[주요 내용]\n" + dto.getMain_content());
			mainChunk.setEmbed_status("PENDING");
			ragDao.insertRagChunks(mainChunk);
		}


		if(dto.getDecisions() != null 
				&& !dto.getDecisions().isEmpty()) {
			RagChunksDTO decisionChunk = new RagChunksDTO();

			decisionChunk.setRag_doc_seq(ragDocSeq);
			decisionChunk.setChunk_index(chunkIndex++);
			decisionChunk.setChunk_text(
					"회의명 : " + dto.getTitle() + "\n" +
							"회의 일자: " + dto.getMeeting_dt() + 
							"\n\n[결정 사항]\n" + dto.getDecisions());
			decisionChunk.setEmbed_status("PENDING");
			ragDao.insertRagChunks(decisionChunk);
		}

		if(dto.getTodos() != null 
				&& !dto.getTodos().isEmpty()) {
			RagChunksDTO todoChunk = new RagChunksDTO();

			todoChunk.setRag_doc_seq(ragDocSeq);
			todoChunk.setChunk_index(chunkIndex++);
			todoChunk.setChunk_text(
					"회의명 : " + dto.getTitle() + "\n" +
							"회의 일자: " + dto.getMeeting_dt() + 
							"\n\n[할 일]\n" + dto.getTodos());
			todoChunk.setEmbed_status("PENDING");
			ragDao.insertRagChunks(todoChunk);
		}

		embedChunk(ragDocSeq, dto.getTitle());
	}

	public void embedChunk(Long rag_doc_seq, String fileName) {
		List<RagChunksDTO> chunks = ragDao.findChunksByRagDocSeq(rag_doc_seq);

		List<EmbedChunkDTO> items = new ArrayList<>();

		for(RagChunksDTO chunk : chunks) {
			EmbedChunkDTO dto = new EmbedChunkDTO();

			dto.setChunk_seq(chunk.getChunk_seq());
			dto.setRag_doc_seq(chunk.getRag_doc_seq());
			dto.setFile_name(fileName);
			dto.setChunk_text(chunk.getChunk_text());

			items.add(dto);
		}

		EmbedRequestDTO request = new EmbedRequestDTO();

		request.setChunks(items);
		EmbedResponseDTO response =
				restClient.post()
				.uri("/embed/chunks")
				.body(request)
				.retrieve()
				.body(EmbedResponseDTO.class);
		if(response != null && response.isSuccess()) {
			for(PointInfoDTO point : response.getPoints()) {
				ragDao.updateChunkEmbed(point.getChunk_seq(),point.getPoint_id());
			}
		}
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

	@Transactional
	public void deleteChat(Long chat_seq) {
		aiDao.deleteQuestions(chat_seq);
		aiDao.deleteAiMessages(chat_seq);
		aiDao.deleteAiChat(chat_seq);
	}

	@Transactional
	public void deleteDocumentRag(Long document_seq) {
		Long ragDocSeq = ragDao.findRagDocSeq("DOCUMENTS", document_seq);

		List<String> pointIds = ragDao.findPointIdsByRagDocSeq(ragDocSeq);

		DeletePointIdsRequestDTO request = new DeletePointIdsRequestDTO();
		request.setPoint_ids(pointIds);

		restClient.post()
		.uri("/delete/points")
		.body(request)
		.retrieve()
		.toBodilessEntity();

		ragDao.deleteRagChunksByRagDocSeq(ragDocSeq);
		ragDao.deleteRagDocumentsByRagDocSeq(ragDocSeq);
	}

	@Transactional
	public void deleteMeetingRag(Long minute_seq) {
		Long ragDocSeq = ragDao.findRagDocSeq("MEETING_MINUTES", minute_seq);

		List<String> pointIds = ragDao.findPointIdsByRagDocSeq(ragDocSeq);

		DeletePointIdsRequestDTO request = new DeletePointIdsRequestDTO();
		request.setPoint_ids(pointIds);

		restClient.post()
		.uri("/delete/points")
		.body(request)
		.retrieve()
		.toBodilessEntity();

		ragDao.deleteRagChunksByRagDocSeq(ragDocSeq);
		ragDao.deleteRagDocumentsByRagDocSeq(ragDocSeq);
	}


}
