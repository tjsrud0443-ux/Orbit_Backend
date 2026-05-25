package com.study.app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.study.app.interceptors.TokenValidator;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	
	@Autowired
	private TokenValidator interceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/auth/login")
		.excludePathPatterns("/signup/**")
		.excludePathPatterns("/admin/**");
	}
}
