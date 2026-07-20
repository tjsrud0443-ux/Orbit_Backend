package com.study.app.domains.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersRoleService {
	@Autowired
	private UsersRoleDAO dao;
	
	@Autowired
	private UsersService usersServ; 
	
	public List<String> getUserRoles(String users_id) {
        return dao.getUserRoles(users_id);
    }
	
	@Transactional
	public void updateUserRoles(String users_id, List<String> roles) {
	    dao.deleteAllByUsersId(users_id);//수정시 현재 권한 모두 삭제
	    if (roles != null) {// 중복 방지 
	        List<String> distinctRoles = new ArrayList<>();
	        for (String auth_group : roles) {
	            if (auth_group == null || auth_group.isBlank()) {
	                continue; // 값이 없으면 건너뜀
	            }
	            if (!distinctRoles.contains(auth_group)) {
	                distinctRoles.add(auth_group); // 아직 없는 값만 추가
	            }
	        }

	        for (String auth_group : distinctRoles) {
	            dao.insertUserRole(users_id, auth_group);
	        }
	    }
	}
	
	// 부서 권한 + 개인 권한 합쳐서 인사 권한 여부 판단
	public boolean isHrAuthorized(String loginId) {
	    UsersDTO userInfo = usersServ.getUsersInfo(loginId);
	    if (userInfo == null) return false;

	    String deptAuth = userInfo.getAuth_group();        // 부서 권한 (단일 값)
	    String personalAuth = userInfo.getUser_auth_group(); // 개인 권한 (콤마로 이어붙인 문자열)

	    boolean deptOk = "ROLE_HR_ADMIN".equals(deptAuth) || "ROLE_SUPER_ADMIN".equals(deptAuth);
	    boolean personalOk = personalAuth != null &&
	            (personalAuth.contains("ROLE_HR_ADMIN") || personalAuth.contains("ROLE_SUPER_ADMIN"));

	    return deptOk || personalOk;
	}
}
