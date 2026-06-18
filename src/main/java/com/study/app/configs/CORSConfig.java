package com.study.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedMethods("*")
		.allowedHeaders("*")
		.allowedOrigins("https://sukong.shop")
		.allowCredentials(true); // 쿠키나 인증 헤더(Authorization)를 통신에 포함할지 여부 (로그인 연동 시 필요)
	}
}
