package com.study.app.domains.aiChat;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class DbRagService {
	
	private final ChatClient chatClient;
	private final DBTool dbTool;
	
	
	@Value("classpath:ai/orbit_schema.md")
	private Resource schemaResource;
	
	public DbRagService(
			@Qualifier("googleGenAiChatModel") ChatModel chatModel,
			DBTool dbTool) {
		this.chatClient = ChatClient.builder(chatModel).build();
		this.dbTool = dbTool;
	}
	
	private String getSchema() {
		try {
			return StreamUtils.copyToString(
					schemaResource.getInputStream(),
					StandardCharsets.UTF_8
			);
		}catch(Exception e) {
			e.printStackTrace();
			return "스키마 정보를 불러오지 못했습니다";
		}
	}
	
	public String answer(String loginId, String content) {
        String schema = getSchema();
        String today = LocalDate.now().toString();
        
        String systemPrompt = """
        		당신은 사내 그룹웨어 Orbit의 DB 조회형 AI입니다.
        		
        		[역할]
        		- 사용자의 질문이 DB에 저장된 정형 데이터로 답변이 가능한 경우, 제공된 VIEW 스키마를 참고하여 필요한 SELECT 조회를 수행합니다.
        		- 조회 결과를 바탕으로 사용자가 이해하기 쉬운 한국어 및 자연어 답변을 생성합니다.
        		- 반드시 Oracle 11g XE 문법만 사용합니다.
        		
        		[중요 규칙]
        		1. 반드시 SELECT 조회만 수행하세요.
        		2. INSERT, UPDATE, DELETE, DROP, ALTER, CREATE는 절대 사용하지 마세요.
        		3. 반드시 V_AI_로 시작하는 VIEW만 조회하세요.
        		4. 원본 테이블 USERS, SIGNUP, AI_MESSAGES 등은 절대 직접 조회하지 마세요.
        		5. 사용자가 '나', '내', '본인' 이라고 말하면 현재 로그인 사용자 ID를 기준으로 조회하세요.
        		6. 현재 로그인 사용자 ID는 '%s'입니다.
        		7. 오늘 날짜는 '%s'입니다.
        		8. 문자열 조건에는 반드시 작은따옴표를 사용하세요. 예: EMP_ID = '%s'
        		9. 스키마 문서에 없는 VIEW나 컬럼은 절대 지어내지 마세요.
        		10. 조회 결과가 없으면 조회된 데이터가 없다고 답하세요.
        		11. 최종 답변에는 SQL문을 그대로 노출하지 말고 자연어로만 설명하세요.
        		
        		[DB VIEW 스키마 문서]
        		%s
        		""".formatted(loginId, today, loginId, schema);
        
        try {
        	String answer = chatClient.prompt()
        			.system(systemPrompt)
        			.user(content)
        			.tools(dbTool)
        			.call()
        			.content();
        	
        	if(answer == null || answer.isBlank()) {
        		return "답변을 생성하지 못했습니다.";
        	}
        	
        	return answer;
        	
        }catch(Exception e) {
        	e.printStackTrace();
        	return "DB 조회 중 오류가 발생했습니다. 잠시 후 다시 시도해 주세요";
        }
	}
}