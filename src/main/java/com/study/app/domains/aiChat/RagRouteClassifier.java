package com.study.app.domains.aiChat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RagRouteClassifier {
	
	private final ChatClient gemini;
	
	public RagRouteClassifier(
			@Qualifier("googleGenAiChatModel") ChatModel chatModel) {
		this.gemini = ChatClient.builder(chatModel).build();
	}
	
	public String classify(String message) {
		if(message == null || message.isBlank()) {
			return "UNKNOWN";
		}
		
		String systemPrompt = """
				당신은 사내 그룹웨어 Orbit 챗봇의 질문 분류기입니다.
				
				사용자의 질문을 보고 반드시 아래 셋 중 하나로만 분류하세요.
				
				[DB]
				현재 DB에 저장된 정형 데이터를 조회해야 하는 질문입니다.
				예:
				- 내 연차 얼마나 남았어?
				- 쉬는 날 얼마나 남았어?
				- 내 부서와 직급이 뭐야?
				- 내 총 연차와 잔여 연차를 알려줘
				- 휴가 잔여일 알려줘
				- 오늘 내 출근 기록 있어?
				- 이번 달 내 근무 기록 보여줘
				- 내 퇴근 정정 신청 상태 알려줘
				- 내 연장근무 신청 승인됐어?
				- 내가 올린 결재 문서 상태 알려줘
				- 내가 결재해야 할 문서 있어?
				- 오늘 내 일정 뭐 있어?
				- 오늘 할 일 알려줘
				- 이번 달 회사 일정 알려줘
				- 회의실 예약 현황 알려줘
				- 내가 예약한 회의실 목록을 알려줘
				- 내가 참여 중인 프로젝트 알려줘
				- 특정 프로젝트 기간 알려줘
				- 내가 맡은 작업 뭐 있어?
				- 이번주 마감인 작업이 있어?
				- 우선순위 높은 내 작업 알려줘.
				- 노트북 재고 있어?
				- 안 읽은 알림 있어?
				- 인사팀 직원 목록 알려줘
				- 오늘 회의 일정 알려줘
				
				
				[DOC]
				사내 문서, 회의록, 규정, 정책, 메뉴얼, 가이드, 절차, 문서 본문 요약이 필요한 질문입니다.
				예:
				- 연차 규정에 대해 알려줘
				- 연차 신청 방법에 대해 알려줘
				- 지출결의서 결재라인 알려줘
				- 회의록 주요 내용 요약해줘
				- 사내 규정 알려줘
				- 문서 내용 요약해줘
				- 6월 25일 회의록들에 대해 할 일을 정리해줘.
		
				
				[UNKNOWN]
				DB 조회인지 DOC 검색인지 판단하기 어려운 질문입니다.
				예:
				- 너 뭐 할 수 있어?
				- 고마워
				- 미안해
				- 점심 메뉴 추천해줘
				
				출력 규칙:
				- 반드시 DB, DOC, UNKNOWN 중 하나만 출력하세요.
				- 설명 문장, 마침표, 따옴표는 붙이지 마세요.
				""";
		
		try {
			String result = gemini.prompt()
					.system(systemPrompt)
					.user(message)
					.call()
					.content();
			
			if(result == null || result.isBlank()) {
				return "UNKNOWN";
			}
			
			String route = result.trim().toUpperCase();
			
			if("DB".equals(route) || route.contains("DB")) {
				return "DB";
			}
			
			if("DOC".equals(route) || route.contains("DOC")) {
				return "DOC";
			}
			
			if ("UNKNOWN".equals(route)) {
				return "UNKNOWN";
			}
			
			return "UNKNOWN";
		}catch(Exception e) {
			e.printStackTrace();
			return "UNKNOWN";
		}
	}
}
