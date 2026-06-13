package com.study.app.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSorkerEvnetListener {
	
	// 연결 시도 감지
	@EventListener
	public void handleWebSocketConnect(SessionConnectEvent e) {
//		System.out.println("연결 시도 중");
	}
	
	@EventListener
	public void handleWebSocketConnected(SessionConnectedEvent e) {
//		System.out.println("연결 완료");
	}
	
	@EventListener
	public void handleWebSocketDisconnect(SessionDisconnectEvent e) {
//		System.out.println("연결 종료");
	}
}
