package com.study.app.domains.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsersRoleDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 사용자 권한 목록 조회
    public List<String> getUserRoles(String users_id) {
        return mybatis.selectList("UsersRole.getUserRoles", users_id);
    }
    
    // 특정 사용자의 권한 전체 삭제(권한 수정 말고 지웠다 다시 넣음)
    public void deleteAllByUsersId(String users_id) {
    	mybatis.delete("UsersRole.deleteAllByUsersId", users_id);
    }

    // 사용자 권한 등록 
    public void insertUserRole(String users_id, String auth_group) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("users_id", users_id);
        paramMap.put("auth_group", auth_group);
        
        mybatis.insert("UsersRole.insertUserRole", paramMap);
    }
}
