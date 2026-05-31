package com.study.app.domains.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<UsersDTO> getAllEmployees(){
		return dao.getAllEmployees();
	}
	
	public UsersDTO getMyPageInfo(String loginId) {
		return dao.getMyPageInfo(loginId);
	}
}
