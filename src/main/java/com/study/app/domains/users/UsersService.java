package com.study.app.domains.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.util.JWTUtil;

@Service
public class UsersService {
	
	@Autowired
	private UsersDAO dao;
	
	public UsersDTO getHrInfo(String id) {
		return dao.getHrInfo(id);
	}
	
	public UsersDTO getUsersInfo(String loginId) {
		return dao.getUsersInfo(loginId);
	}
}
