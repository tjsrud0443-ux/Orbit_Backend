package com.study.app.domains.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
	
	@Autowired
	private UsersDAO dao;
	
	public UsersDTO getHrInfo(String id) {
		return dao.getHrInfo(id);
	}
}
