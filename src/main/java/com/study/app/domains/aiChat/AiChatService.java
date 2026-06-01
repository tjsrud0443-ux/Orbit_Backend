package com.study.app.domains.aiChat;

import java.util.List;
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

@Service
public class AiChatService {

	@Autowired
	private VectorStore vectorStore;

	@Autowired
	@Qualifier("googleGenAiChatModel")
	private ChatModel chatModel;

	@Autowired
	private AiChatDAO aiDao;

	public String getRagResponse(String role, String content) {
		
//		aiDao.insertAiChat();
		
		List<Document> similarDocs = vectorStore.similaritySearch(
				SearchRequest
				.builder()
				.query(content)
				.topK(3)
				.build());

		if(similarDocs.isEmpty()) {

			return "사내 데이터베이스에서 요청하신 규정이나 가이드를 찾지 못했습니다. 😢\n"
					+ "정확한 안내를 위해 해당 질문은 관리자에게 문의해 주세요.";

		}

		String context = similarDocs.stream().map(Document::getFormattedContent)
				.collect(Collectors.joining("\n\n"));

		String systemPrompt = "당신은 사내 그룹웨어 시스템의 스마트 업무 지원 AI 비서입니다."
				+ "임직원이 안심하고 업무 가이드를 얻을 수 있도록, 아래 제공된 [사내 문서 데이터]만을 바탕으로 정중하고 객관적으로 답변해야 합니다."
				+ ""
				+ "[업무 가이드라인 및 주의사항]"
				+ "1. 철저하게 제공된 [사내 문서 데이터] 내의 공식 규정, 절차, 복리후생 내용에 기반하여 답변하세요."
				+ "2. 문서 내용으로 확답을 줄 수 없거나 정보가 부족한 경우, 절대 상상해서 거짓 정보를 안내하지 마세요."
				+ "(예: 해당 사항에 대한 공식 규정 문서가 확인되지 않습니다. 정확한 사항은 관리자에게 따로 문의가 필요합니다. 라고 정중히 안내할 것."
				+ "3. 임직원을 대하는 격식있고 명확한 비즈니스 어조(한글 존댓말 씁니다.)를 유지하세요."
				+ "4. 가독성을 위해 항목별로 줄바꿈을 적절히 활용하여 정돈된 형태로 출력하세요."
				+ ""
				+ "[사내 문서 데이터]"
				+ "%s".formatted(context);

		Prompt prompt = new Prompt(List.of(
				new SystemMessage(systemPrompt),
				new UserMessage(content)));

		ChatResponse response = chatModel.call(prompt);
		String aiAnswer = response.getResult().getOutput().getText();
		
		return aiAnswer;
	}


}
