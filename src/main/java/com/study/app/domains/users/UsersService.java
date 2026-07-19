package com.study.app.domains.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.file.FileService;

@Service
public class UsersService {
	
	@Autowired
	private UsersDAO dao;
	@Autowired
	private FileService fileServ;
	
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
	    int activeCount = dao.getCountByStatus("ACTIVE");
	    int inactiveCount = dao.getCountByStatus("INACTIVE");
	    int retireCount = dao.getCountByStatus("RETIRE");

	    Map<String, Object> result = new HashMap<>();
	    result.put("users", users);
	    result.put("totalCount", totalCount);
	    result.put("activeCount", activeCount);
	    result.put("inactiveCount", inactiveCount);
	    result.put("retireCount", retireCount);

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
	
	public Map<String, String> uploadUserStamp(String loginId, MultipartFile file) throws Exception {
	    Map<String, String> uploadResult = fileServ.upload(file);
	    String sysname = uploadResult.get("sysname");
	    String oriname = uploadResult.get("oriname");
	    // insert/update 분기
	    int exists = dao.existsUserStamp(loginId);
	    if (exists > 0) {
	        dao.updateUserStamp(loginId, sysname, oriname);
	    } else {
	        dao.insertUserStamp(loginId, sysname, oriname);
	    }
	    
	    return uploadResult;
	}
	
	public String selectUserStampSysname(String users_id) {
		return dao.selectUserStampSysname(users_id);
	}
	
	@Transactional
	public Map<String, String> updateProfileFile(String loginId, MultipartFile file) throws Exception{
		String oldSysname = dao.findProfileSysname(loginId);

		Map<String, String> uploadResult = fileServ.upload(file);
		String newSysname = uploadResult.get("sysname");
		String newOriname = uploadResult.get("oriname");

		Map<String, Object> params = new HashMap<>();
		params.put("loginId", loginId);
		params.put("sysname", newSysname);
		params.put("oriname", newOriname);

		try {
			int result = dao.updateProfileImage(params);
			if(result == 0) {
				throw new IllegalStateException("프로필 이미지를 수정할 사용자를 찾을 수 없습니다");
			}
		}catch(Exception e) {
			fileServ.deleteFromGCS(newSysname);
			throw e;
		}

		if(oldSysname != null && !oldSysname.isBlank()) {
			try {
				fileServ.deleteFromGCS(oldSysname);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return uploadResult;
	}
}
