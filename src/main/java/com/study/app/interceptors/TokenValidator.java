package com.study.app.interceptors;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.study.app.domains.users.UsersDTO;
import com.study.app.domains.users.UsersService;
import com.study.app.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenValidator implements HandlerInterceptor{

	@Autowired
	private JWTUtil jwt;

	@Autowired
	private UsersService usersServ;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception{

		if(request.getMethod().equalsIgnoreCase("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
			return true;
		}

		String token = "";
		String authHeader = request.getHeader("Authorization");
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		}else {
			token = request.getParameter("token");
		}

		if(token != null && !token.isEmpty() && !"null".equals(token) && !"undefined".equals(token)) {
			try {
				String id = jwt.getSubject(token);
				request.setAttribute("loginId", id);

				UsersDTO userInfo = usersServ.getUsersInfo(id);

				if(userInfo == null) {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return false;
				}

				String deptAuth = userInfo.getAuth_group();
				String userAuthGroup = userInfo.getUser_auth_group();
				String uri = request.getRequestURI();
				
				boolean isSuperAdmin = 
						"ROLE_SUPER_ADMIN".equals(deptAuth)
						|| hasAuth(userAuthGroup, "ROLE_SUPER_ADMIN");
				
				boolean isHrAdmin = 
						"ROLE_HR_ADMIN".equals(deptAuth)
						|| hasAuth(userAuthGroup, "ROLE_HR_ADMIN");
				
				boolean isGaAdmin = 
						"ROLE_GA_ADMIN".equals(deptAuth)
						|| hasAuth(userAuthGroup, "ROLE_GA_ADMIN");
				
				boolean isFnAdmin = 
						"ROLE_FN_ADMIN".equals(deptAuth)
						|| hasAuth(userAuthGroup, "ROLE_FN_ADMIN");
				
				if(isSuperAdmin) {return true;}

				if(uri.startsWith("/admin/hr") && !isHrAdmin) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN,
											"인사 관리 권한이 없습니다.");
						return false;
				}

				if(uri.startsWith("/admin/ga") && !isGaAdmin) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN,
											"총무 관리 권한이 없습니다.");
						return false;
				}

				return true;
			}catch(Exception e) {
				e.printStackTrace();
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}
		}
		return false;
	}
	
	private boolean hasAuth(String userAuthGroup, String targetAuth) {
		if(userAuthGroup == null || userAuthGroup.isBlank()) {
			return false;
		}
		
		return Arrays.stream(userAuthGroup.split(","))
				.map(String::trim)
				.anyMatch(targetAuth::equals);
	}
}
