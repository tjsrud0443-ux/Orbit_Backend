package com.study.app.domains.users;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.app.commons.EncryptionUtils;
import com.study.app.domains.auth.AuthDTO;

@Repository
public class UsersDAO {

	@Autowired
	private SqlSessionTemplate batis;
	
	public int login(AuthDTO dto) {
		String getShaPw = EncryptionUtils.getSha512(dto.getPw());
		dto.setPw(getShaPw);
		return batis.selectOne("Users.login", dto);
	}
	
}
