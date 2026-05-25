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
		if(authHeader != null && authHeader.startsWith("Bearer")) {
			token = authHeader.substring(7);
		}else {
			token = request.getParameter("token");
		}

		if(token != null) {
			try {
				String id = jwt.getSubject(token);
				request.setAttribute("loginId", id);

				// ============ 직급, 팀별 권한 제어
				// 입력한 api 파악 
				String uri = request.getRequestURI();


				if(uri.startsWith("/admin")) {
					// ID , 이름, 부서명, 직급, 권한 뽑아오는 메서드
					UsersDTO userInfo = usersServ.getUsersInfo(id);
					if(userInfo == null) {
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						return false;
					}

					String dept = userInfo.getDept_name();
					String rank = userInfo.getRank_name();
					String role = userInfo.getRole();

					boolean isMaster = 
							"ADMIN".equals(role) || "운영총괄본부".equals(dept) || "운영총괄팀".equals(dept);

					if(!isMaster) {
						// 직원 관리, 부서 관리, 회원가입 관리 권한 확인
						if(uri.startsWith("/adminUsers") || uri.startsWith("/adminDepartments") || 
								uri.startsWith("/adminSignup")) {
							if(!"인사팀".equals(dept) && !"대표".equals(rank)) {
								response.sendError(HttpServletResponse.SC_FORBIDDEN, "인사 관리 권한이 없습니다");
								return false;
							}
						}

						// 비품 관리, 비품 신청 관리, 비품 대여이력 관리, 회의실 관리 권한 확인
						if(uri.startsWith("/adminSupply") || uri.startsWith("/adminSupplyRequest") ||
								uri.startsWith("/adminSupplyRental") || uri.startsWith("/adminMeetingRoom")) {
							if(!"총무팀".equals(dept) && !"대표".equals(rank)) {
								response.sendError(HttpServletResponse.SC_FORBIDDEN, "자산 관리 권한이 없습니다");
								return false;
							}
						}

						// 문서 관리, AI 미답변 질문 관리 권한 확인
						if(uri.startsWith("/adminDocument") || uri.startsWith("/adminQna")) {
							if(!"부서장".equals(rank) && !"본부장".equals(rank) && !"대표".equals(rank)) {
								response.sendError(HttpServletResponse.SC_FORBIDDEN, "해당 메뉴의 직급 권한이 없습니다");
								return false;
							}
						}
					}
				}
				return true;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return false;
	}
}
