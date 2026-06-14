package com.study.app.domains.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.aiChat.AiUnansweredQuestionsDTO;
import com.study.app.domains.checkoutRequest.CheckoutRequestService;
import com.study.app.domains.departments.DepartmentsCountDTO;
import com.study.app.domains.departments.DepartmentsDAO;
import com.study.app.domains.departments.DepartmentsDTO;
import com.study.app.domains.departments.DeptLeaveDTO;
import com.study.app.domains.documents.DocumentsDAO;
import com.study.app.domains.documents.DocumentsDTO;
import com.study.app.domains.meetingRooms.MeetingRoomsDTO;
import com.study.app.domains.meetingRooms.MeetingRoomsService;
import com.study.app.domains.overtimeRequest.OvertimeRequestService;
import com.study.app.domains.rank.RankDAO;
import com.study.app.domains.rank.RankDTO;
import com.study.app.domains.signup.SignupDTO;
import com.study.app.domains.signup.SignupRequestDTO;
import com.study.app.domains.signup.SignupService;
import com.study.app.domains.supplies.SupplyDAO;
import com.study.app.domains.supplies.SupplyDTO;
import com.study.app.domains.supplies.SupplyRentalDTO;
import com.study.app.domains.supplies.SupplyRequestDTO;
import com.study.app.domains.supplies.SupplyService;
import com.study.app.domains.users.UsersDAO;
import com.study.app.domains.users.UsersDTO;
import com.study.app.domains.users.UsersService;

@Service
public class AdminService {
	
	@Autowired
	private SignupService signupServ;
	@Autowired
	private UsersService usersServ;
	@Autowired
	private MeetingRoomsService roomServ;
	@Autowired
	private SupplyService supplyServ;
	
	@Autowired
	private DepartmentsDAO departmentsDao;
	@Autowired
	private RankDAO rankDao;
	@Autowired
	private UsersDAO usersDao;
	@Autowired
	private AdminDAO adminDao;
	@Autowired
	private DocumentsDAO docDao;
	@Autowired
	private SupplyDAO supplyDAO;
	
	
	public Map<String, Object> getAllRequest(Long cPage, String status, String searchTerm) {
		return signupServ.getAllRequest(cPage, status, searchTerm);
	}
	
	public SignupDTO getUserInfo(Long signup_seq) {
		return signupServ.getUserInfo(signup_seq);
	}
	
	public UsersDTO getHrInfo(String id) {
		return usersServ.getHrInfo(id);
	}
	
	public List<DepartmentsDTO> getDeptList() {
		return departmentsDao.getDeptList();
	}

	public List<RankDTO> getRankList() {
		return rankDao.getRankList();
	}

	public void userSignup(SignupRequestDTO request) {
		signupServ.userSignup(request);
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

	public void rejectSignup(Long signup_seq) {
		signupServ.rejectSignup(signup_seq);
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
	
	public Map<String, Object> getDashboard() {
		Map<String, Object> result = new HashMap<>();

		result.put("allEmployeeCount", adminDao.allEmployeeCount());
		result.put("joinEmployeeCount", adminDao.joinEmployeeCount());
		result.put("resignEmployeeCount", adminDao.resignEmployeeCount());
		result.put("aiQuestionsCount", adminDao.aiQuestionsCount());
		result.put("supplyRequestCount", adminDao.supplyRequestCount());
		
		return result;
	}
	
	public List<DepartmentsCountDTO> deptEmployeeCount() {
		return adminDao.deptEmployeeCount();
	}
	
	public List<DeptLeaveDTO> getDeptLeave() {
		return adminDao.getDeptLeave();
	}

	public Map<String, Object> joinResignCount() {
		Map<String, Object> result = new HashMap<>();
		result.put("joinCount", adminDao.joinResignCount());
		
		return result;
	}
	
	public List<DocumentsDTO> getAllDocs(){
		return docDao.getAllDocs();
	}
	
	public List<MeetingRoomsDTO> getAllRooms() {
		return roomServ.getAllRooms();
	}
	
	public void addMeetingRoom(MeetingRoomsDTO dto, MultipartFile file) {
		roomServ.addMeetingRoom(dto, file);
	}
	
	public void editMeetingRoom(MeetingRoomsDTO dto, MultipartFile file) {
		roomServ.editMeetingRoom(dto, file);
	}
	
	public void deleteMeetingRoom(Long room_seq) {
		roomServ.deleteMeetingRoom(room_seq);
	}
	
	public List<AiUnansweredQuestionsDTO> getAiQuestions() {
		return adminDao.getAiQuestions();
	}
	
	public List<AiUnansweredQuestionsDTO> myDeptQuestion(Long dept_seq, String auth_group) {
		Map<String, Object> params = new HashMap<>();
		params.put("dept_seq", dept_seq);
		params.put("auth_group", auth_group);
		return adminDao.myDeptQuestion(params);
	}
	
	public void insertUpdateAnswer(AiUnansweredQuestionsDTO dto) {
		adminDao.insertUpdateAnswer(dto);
	}
	
	public void deleteAnswer(Long question_seq) {
		adminDao.deleteAnswer(question_seq);
	}
	
	
/*비품 관련*/
	public List<SupplyDTO> getSupplyList(){
		return supplyServ.getSupplyList();
	}
	
	public void insertSupply(SupplyDTO dto) {
		supplyServ.insertSupply(dto);
	}
	
	public void deleteSupplies(List<Long> ids) {
	    supplyDAO.deleteSupplies(ids);
	}
	
	public void updateSupplies(SupplyDTO dto) {
		supplyServ.updateSupplies(dto);
	}
	
	//비품 신청 관리 목록
	public List<SupplyRequestDTO> getAdminRequestList(Map<String, Object> params){
		return supplyServ.getAdminRequestList(params);
	}
	
	public int getAdminRequestCount(Map<String, Object> params) {
		return supplyDAO.getAdminRequestCount(params);
	}
	
	//비품 신청 상태 및 비품 재고 수정 및 대여이력 insert
	public void approveRequest(SupplyRequestDTO srDto) {
	    supplyServ.approveRequest(srDto);
	}
	
	/*supply rental list*/
	public List<SupplyRentalDTO> supplyRentalList(Map<String, Object> params) {
	    return supplyDAO.supplyRentalList(params);
	}

	public int supplyRentalCount(Map<String, Object> params) {
	    return supplyDAO.supplyRentalCount(params);
	}
	
	//반납 처리 시 재고 복구 & 반납일 update
	public void returnSupply(SupplyRentalDTO dto) {
	    supplyServ.returnSupply(dto);  // @Transactional은 SupplyService에 있으니까 OK
	}

























































































































































































































































































	@Autowired
	private CheckoutRequestService checkoutServ;
	@Autowired
	private OvertimeRequestService overtimeServ;
	
	
	public Map<String, Object> getAllCheckoutRQ(Long cPage, String status) {
		return checkoutServ.getAllCheckoutRQ(cPage, status);
	}
	
	public Map<String, Object> getAllOvertimeRQ(Long cPage, String status) {
		return overtimeServ.getAllOvertimeRQ(cPage, status);
	}
	
	public void approveCheckout(Long checkout_seq, String loginId) {
		checkoutServ.approveCheckout(checkout_seq, loginId);
	}
	
	public void rejectCheckout(Long checkout_seq, String loginId) {
		checkoutServ.rejectCheckout(checkout_seq, loginId);
	}
	
	public void approveOvertime(Long overtime_seq, String loginId) {
		overtimeServ.approveOvertime(overtime_seq, loginId);
	}
	
	public void rejectOvertime(Long overtime_seq, String loginId) {
		overtimeServ.rejectOvertime(overtime_seq, loginId);
	}
}
