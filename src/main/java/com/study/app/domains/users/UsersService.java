package com.study.app.domains.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/*직원관리용 직원 정보 출력*/
	public Map<String, Object> getAllUsers(String keyword, String status, Long start, Long end) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
	    params.put("status", status);
	    params.put("start", start);
	    params.put("end", end);

	    List<UsersDTO> users = dao.getAllUsers(params);
	    int totalCount = dao.getTotalCount(params);
	    int activeCount = dao.getCountByStatus("ACTIVE", params);
	    int inactiveCount = dao.getCountByStatus("INACTIVE", params);
	    int rejectedCount = dao.getCountByStatus("REJECTED", params);

	    Map<String, Object> result = new HashMap<>();
	    result.put("users", users);
	    result.put("totalCount", totalCount);
	    result.put("activeCount", activeCount);
	    result.put("inactiveCount", inactiveCount);
	    result.put("rejectedCount", rejectedCount);

	    return result;
	}
	
	public UsersDTO getMyPageInfo(String loginId) {
		return dao.getMyPageInfo(loginId);
	}
	
	public boolean checkDupEmail(String email, String id) {
		int result = dao.checkDupEmail(email, id);
	    return result > 0;
	}
	
	public int updateMyPageInfo(UsersDTO dto) {
		return dao.updateMyPageInfo(dto);
	}
}
