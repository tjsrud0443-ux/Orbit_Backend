package com.study.app.interceptors;

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

				String rank = userInfo.getRank_name();
				String role = userInfo.getRole();
				String auth = userInfo.getAuth_group();
				
				String uri = request.getRequestURI();

				boolean isAdmin = "ADMIN".equals(role) || "ROLE_SUPER_ADMIN".equals(auth);

				if(isAdmin) {return true;}

				if(uri.startsWith("/admin/hr")) {
					if(!"ROLE_HR_ADMIN".equals(auth) && !"ROLE_SUPER_ADMIN".equals(auth)) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
						return false;
					}
				}

				if(uri.startsWith("/admin/ga")) {
					if(!"ROLE_GA_ADMIN".equals(auth) && !"ROLE_SUPER_ADMIN".equals(auth)) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
						return false;
					}
				}

				if(uri.startsWith("/admin/document")|| uri.startsWith("/admin/ai")) {
					if(!"부서장".equals(rank) && !"본부장".equals(rank) && !"대표".equals(rank)) {
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
						return false;
					}
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
}
