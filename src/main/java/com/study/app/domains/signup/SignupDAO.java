package com.study.app.domains.signup;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SignupDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int isExistId(String id) {
		return mybatis.selectOne("Signup.isExistId", id);
	}
	
	public void signupRequest(SignupDTO dto) {
		mybatis.insert("Signup.signupRequest", dto);
	}
}
