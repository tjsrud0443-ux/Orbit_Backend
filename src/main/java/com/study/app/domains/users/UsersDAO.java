package com.study.app.domains.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
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
	
	public int insertUser(UsersDTO dto) {
		return batis.insert("Users.insertUser", dto);
	}
	
	public UsersDTO getHrInfo(String id) {
		return batis.selectOne("Users.getHrInfo", id);
	}
	
	public UsersDTO getUsersInfo(String loginId) {
		return batis.selectOne("Users.getUsersInfo", loginId);
	}
	
	public List<UsersDTO> getAllUsers(Map<String, Object> params) {
	    return batis.selectList("Users.getAllUsers", params);
	}

	public int getTotalCount(Map<String, Object> params) {
	    return batis.selectOne("Users.getTotalCount", params);
	}

	public int getCountByStatus(String status, Map<String, Object> params) {
	    Map<String, Object> countParams = new HashMap<>(params);
	    countParams.put("status", status);
	    return batis.selectOne("Users.getCountByStatus", countParams);
	}
	
	public int updateUsersState(UsersDTO dto) {
		return batis.update("Users.updateUsersState",dto);
	}
	
	public int updateUsersInfo(UsersDTO dto) {
		return batis.update("updateUsersInfo",dto);
	}
	public List<UsersDTO> getAllEmployees(){
		return batis.selectList("Users.getAllEmployees");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
