package com.study.app.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.study.app.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenValidator implements HandlerInterceptor{

	@Autowired
	private JWTUtil jwt;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception{

		if(request.getMethod().equalsIgnoreCase("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}

		String token = "";
		String authHeader = request.getHeader("Authorization");
		if(authHeader != null && authHeader.startsWith("Bearer")) {
			token = authHeader.substring(7);
		}else {
			token = request.getParameter("token");
		}

		if(token != null) {
			try {
				String id = jwt.getSubject(token);
				request.setAttribute("loginId", id);
				return true;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return false;
	}
}
