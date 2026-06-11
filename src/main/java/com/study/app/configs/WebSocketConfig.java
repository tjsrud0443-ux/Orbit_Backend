package com.study.app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.study.app.interceptors.JwtInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{ //웹소켓 관련 설정 파일
	
	@Autowired
	private JwtInterceptor jwtInterceptor;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// ↓ publish, 알림 보낼 목적지의 접두사 지정
		registry.setApplicationDestinationPrefixes("/pub");
		// ↓ subscribe, 사용자가 구독할 채널명 (목적지) 들의 접두사 지정
		registry.enableSimpleBroker("/sub");
	}
	
	// 웹소켓 연결 앤드포인트 설정
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/web") // 프론트 brokerURL 과 동일하게 설정 필요!
		.setAllowedOriginPatterns("*"); // React URL이 들어가야 할 자리. *React로 들어오는 웹소켓만 허용하겠다는 것.
	}
	
	// 웹소켓 검사
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(jwtInterceptor);
	}
}
