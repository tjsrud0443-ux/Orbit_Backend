package com.study.app.domains.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.departments.DepartmentsDAO;
import com.study.app.domains.departments.DepartmentsDTO;
import com.study.app.domains.rank.RankDAO;
import com.study.app.domains.rank.RankDTO;
import com.study.app.domains.signup.SignupDAO;
import com.study.app.domains.signup.SignupDTO;
import com.study.app.domains.signup.SignupRequestDTO;
import com.study.app.domains.users.UsersDAO;
import com.study.app.domains.users.UsersDTO;

@Service
public class AdminService {

	@Autowired
	private DepartmentsDAO departmentsDao;
	@Autowired
	private RankDAO rankDao;
	@Autowired
	private UsersDAO usersDao;
	@Autowired
	private SignupDAO signupDao;
	@Autowired
	private AdminDAO adminDao;

	public List<DepartmentsDTO> getDeptList() {
		return departmentsDao.getDeptList();
	}

	public List<RankDTO> getRankList() {
		return rankDao.getRankList();
	}

	public void userSignup(SignupRequestDTO request) {
		SignupDTO signupInfo = signupDao.getUserInfo(request.getSignup_seq());

		UsersDTO dto = new UsersDTO();

		dto.setId(signupInfo.getId());
		dto.setPw(signupInfo.getPw());
		dto.setName(signupInfo.getName());
		dto.setPhone(signupInfo.getPhone());
		dto.setEmail(signupInfo.getEmail());
		dto.setOriname(signupInfo.getOriname());
		dto.setSsn_hash(signupInfo.getSsn_hash());
		dto.setSsn_enc(signupInfo.getSsn_enc());
		dto.setSsn_masked(signupInfo.getSsn_masked());
		dto.setZonecode(signupInfo.getZonecode());
		dto.setAddress1(signupInfo.getAddress1());
		dto.setAddress2(signupInfo.getAddress2());
		dto.setSysname(signupInfo.getSysname());

		dto.setHire_date(request.getHire_date());
		dto.setDept_seq(request.getDept_seq());
		dto.setRank_seq(request.getRank_seq());

		usersDao.insertUser(dto);

		signupDao.updateStatusToApproved(request.getSignup_seq());
	}

	public Map<String, Object> getAllUsers(String keyword, String status, Long start, Long end) {
		Map<String, Object> params = new HashMap<>();
		params.put("keyword", keyword);
		params.put("status", status);
		params.put("start", start);
		params.put("end", end);

		List<UsersDTO> users = usersDao.getAllUsers(params);
		int totalCount = usersDao.getTotalCount(params);
		int activeCount = usersDao.getCountByStatus("ACTIVE", params);
		int inactiveCount = usersDao.getCountByStatus("INACTIVE", params);
		int rejectedCount = usersDao.getCountByStatus("REJECTED", params);

		Map<String, Object> result = new HashMap<>();
		result.put("users", users);
		result.put("totalCount", totalCount);
		result.put("activeCount", activeCount);
		result.put("inactiveCount", inactiveCount);
		result.put("rejectedCount", rejectedCount);

		return result;
	}


	public int updateUsersState(UsersDTO dto) {
		return usersDao.updateUsersState(dto);
	}

	public int updateUsersInfo(UsersDTO dto) {
		return usersDao.updateUsersInfo(dto);
	}

	public void addDept(DepartmentsDTO dto) {
		
		if("HQ".equals(dto.getDept_type())) {
			adminDao.addDept(dto);
		}else if("SUB".equals(dto.getDept_type())) {
			adminDao.addTeam(dto);
		}

	}
	
	public void delDept(Long dept_seq) {
		adminDao.delDept(dept_seq);
	}
	
	public void updateDept(DepartmentsDTO dto) {
		adminDao.updateDept(dto);
	}

}
