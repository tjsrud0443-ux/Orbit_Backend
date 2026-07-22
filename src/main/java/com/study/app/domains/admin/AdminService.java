package com.study.app.domains.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.aiChat.AiUnansweredQuestionsDTO;
import com.study.app.domains.annualLeave.AdminLeaveDTO;
import com.study.app.domains.annualLeave.AnnualLeaveService;
import com.study.app.domains.certType.CertIssueHistoryDTO;
import com.study.app.domains.certType.CertIssueRequestDTO;
import com.study.app.domains.certType.CertTypeDTO;
import com.study.app.domains.certType.CertTypeService;
import com.study.app.domains.checkoutRequest.CheckoutRequestService;
import com.study.app.domains.companyInfo.CompanyInfoDAO;
import com.study.app.domains.companyInfo.CompanyInfoDTO;
import com.study.app.domains.defaultApprovalLine.DefaultApprovalLineDTO;
import com.study.app.domains.defaultApprovalLine.DefaultApprovalLineService;
import com.study.app.domains.departments.DepartmentsCountDTO;
import com.study.app.domains.departments.DepartmentsDAO;
import com.study.app.domains.departments.DepartmentsDTO;
import com.study.app.domains.departments.DeptLeaveDTO;
import com.study.app.domains.documents.DocumentsDAO;
import com.study.app.domains.documents.DocumentsDTO;
import com.study.app.domains.file.FileService;
import com.study.app.domains.meetingRooms.MeetingRoomsDTO;
import com.study.app.domains.meetingRooms.MeetingRoomsService;
import com.study.app.domains.overtimeRequest.OvertimeRequestService;
import com.study.app.domains.pageInfo.PageInfoDTO;
import com.study.app.domains.pageInfo.PageInfoService;
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
import com.study.app.domains.users.UsersRoleService;
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
	private CheckoutRequestService checkoutServ;
	@Autowired
	private OvertimeRequestService overtimeServ;
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
	@Autowired
	private CompanyInfoDAO comDao;
	@Autowired
	private FileService fileServ;
	@Autowired
	private PageInfoService pageServ;
	@Autowired
	private DefaultApprovalLineService defaultServ;
	@Autowired
	private AnnualLeaveService annualLeaveServ;
	@Autowired
	private CertTypeService certServ;

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
		return usersServ.getAllUsers(keyword, status, start, end);
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
	
	@Transactional
	public void addDept(DepartmentsDTO dto) {
		if (!"HQ".equals(dto.getDept_type())
	            && !"SUB".equals(dto.getDept_type())) {
	        throw new IllegalArgumentException(
	            "올바르지 않은 조직 구분입니다."
	        );
	    }

	    if ("SUB".equals(dto.getDept_type())
	            && dto.getParent_dept_seq() == null) {
	        throw new IllegalStateException(
	            "상위 본부를 선택해 주세요."
	        );
	    }

	    adminDao.addDept(dto);
	}
	
	@Transactional
	public void deleteDept(Long dept_seq) {
		adminDao.deleteDefaultApprovalLineByDeptSeq(dept_seq);
		int result = adminDao.deleteDept(dept_seq);
		
		if(result == 0) {
			throw new IllegalStateException("삭제할 부서를 찾을 수 없습니다.");
		}
	}

	@Transactional
	public void updateDept(DepartmentsDTO dto) {
		DepartmentsDTO currentDept = adminDao.findDeptBySeq(dto.getDept_seq());

		    if (currentDept == null) {
		        throw new IllegalStateException("수정할 부서를 찾을 수 없습니다.");
		    }

		    boolean currentIsHq = adminDao.isHq(dto.getDept_seq()) > 0;

		    boolean changingHqToSub = currentIsHq && "SUB".equals(dto.getDept_type());

		    if (changingHqToSub) {
		        int childCount = adminDao.countChildDepartments(dto.getDept_seq());

		        if (childCount > 0) {
		            throw new IllegalStateException("하위 부서가 존재하는 본부는 부서로 변경할 수 없습니다.");
		        }
		    }

		    if ("SUB".equals(dto.getDept_type())) {
		        if (dto.getParent_dept_seq() == null) {
		            throw new IllegalStateException("상위 본부를 선택해 주세요.");
		        }

		        if (dto.getDept_seq().equals(dto.getParent_dept_seq())) {
		            throw new IllegalStateException("자기 자신을 상위 본부로 지정할 수 없습니다.");
		        }
		    }

		    if (!"HQ".equals(dto.getDept_type())
		            && !"SUB".equals(dto.getDept_type())) {
		        throw new IllegalArgumentException("올바르지 않은 조직 구분입니다.");
		    }

		    int result = adminDao.updateDept(dto);

		    if (result == 0) {
		        throw new IllegalStateException("부서 수정에 실패했습니다.");
		    }
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

	public List<AiUnansweredQuestionsDTO> myDeptQuestion(Long dept_seq, boolean is_super_admin) {
		Map<String, Object> params = new HashMap<>();
		params.put("dept_seq", dept_seq);
		params.put("is_super_admin", is_super_admin);
		return adminDao.myDeptQuestion(params);
	}

	public void insertUpdateAnswer(AiUnansweredQuestionsDTO dto) {
		adminDao.insertUpdateAnswer(dto);
	}

	public void deleteAnswer(Long question_seq) {
		adminDao.deleteAnswer(question_seq);
	}

	public AiQuestionCountDTO adminAiQuestionsData(Long dept_seq, boolean is_super_admin) {
		Map<String, Object> params = new HashMap<>();
		params.put("dept_seq", dept_seq);
		params.put("is_super_admin", is_super_admin);

		return adminDao.adminAiQuestionsData(params);
	}

	/*비품 관련*/
	public List<SupplyDTO> getSupplyList(){
		return supplyServ.getSupplyList();
	}

	public void insertSupply(SupplyDTO dto) {
		supplyServ.insertSupply(dto);
	}

	public void deleteSupplies(List<Long> ids) {
		supplyDAO.deleteSupplyReqItemsByIds(ids);//자식들 먼저 삭제
		supplyDAO.deleteSupplyRentalsByIds(ids);
		supplyDAO.deleteOrphanRequests();// 아이템 없어진 신청건 삭제
		supplyDAO.deleteSupplies(ids);//부모 나중
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

	public CompanyInfoDTO getCompanyInfo() {
		return comDao.getCompanyInfo();
	}

	public void insertCompanyInfo(CompanyInfoDTO dto) {
		comDao.insertCompanyInfo(dto);
	}

	public void updateCompanyInfo(CompanyInfoDTO dto, String loginId) {
		Map<String, Object> params = new HashMap<>();
		params.put("dto", dto);
		params.put("loginId", loginId);
		comDao.updateCompanyInfo(params);
	}

	public void updateCompanyStamp(String loginId, MultipartFile file) throws Exception{
		CompanyInfoDTO beforeInfo = comDao.getCompanyInfo();

		if (beforeInfo == null) {
			throw new IllegalStateException("회사 정보가 먼저 등록되어야 합니다.");
		}

		String beforeSysname = beforeInfo.getOfficialsealSysname();

		Map<String, String> uploadResult = fileServ.upload(file);
		String newSysname = uploadResult.get("sysname");

		try {
			Map<String, Object> params = new HashMap<>();
			params.put("companySeq", beforeInfo.getCompanySeq());
			params.put("oriname", uploadResult.get("oriname"));
			params.put("sysname", uploadResult.get("sysname"));
			params.put("loginId", loginId);

			comDao.updateCompanyStamp(params);

			if (beforeSysname != null && !beforeSysname.isBlank()) {
				fileServ.deleteFromGCS(beforeSysname);
			}
		}catch(Exception e) {
			fileServ.deleteFromGCS(newSysname);
		}
	}

	public void updateCompanyWatermark(String loginId, MultipartFile file) throws Exception{
		CompanyInfoDTO beforeInfo = comDao.getCompanyInfo();

		if (beforeInfo == null) {
			throw new IllegalStateException("회사 정보가 먼저 등록되어야 합니다.");
		}

		String beforeSysname = beforeInfo.getOfficialmarkSysname();

		Map<String, String> uploadResult = fileServ.upload(file);
		String newSysname = uploadResult.get("sysname");

		try {
			Map<String, Object> params = new HashMap<>();
			params.put("companySeq", beforeInfo.getCompanySeq());
			params.put("oriname", uploadResult.get("oriname"));
			params.put("sysname", uploadResult.get("sysname"));
			params.put("loginId", loginId);

			comDao.updateCompanyWatermark(params);

			if (beforeSysname != null && !beforeSysname.isBlank()) {
				fileServ.deleteFromGCS(beforeSysname);
			}
		}catch(Exception e) {
			fileServ.deleteFromGCS(newSysname);
		}
	}
	
	public void insertRank(RankDTO dto) {
		rankDao.insertRank(dto);
	}
	
	public void updateRank(RankDTO dto) {
		rankDao.updateRank(dto);
	}
	
	@Transactional
	public void deleteRank(Long rank_seq) {
		int userCount = rankDao.countUsersByRank(rank_seq);
		if(userCount > 0) {
			throw new IllegalStateException(
					"해당 직급을 사용하는 직원이 있어 삭제할 수 없습니다.\n"
					+ "해당 직급에 포함된 직원을\n다른 직급으로 변경 후 다시 시도해 주세요."
			);
		}
		rankDao.deleteDefaultApprovalLineByRankSeq(rank_seq);
		int result = rankDao.deleteRank(rank_seq);
		if(result == 0) {
			throw new IllegalStateException(
					"삭제할 직급을 찾을 수 없습니다."
			);
		}
		rankDao.reorderRanks();
	}
	
	@Transactional
	public void updateRankOrder(List<RankDTO> list) {
		if(list == null || list.isEmpty()) {
			throw new IllegalStateException("변경할 직급 순서가 없습니다.");
		}
		
		for(RankDTO rank : list) {
			int result = rankDao.updateRankOrder(rank);
			
			if(result == 0) {
				throw new IllegalStateException("직급 순서를 변경하지 못했습니다.");
			}
		}
	}

	public void updatePageInfo(Long page_seq, PageInfoDTO dto) {
		pageServ.updatePageInfo(page_seq, dto);
	}

	public void updateCategory(String oldCategoryName, String newCategoryName) {
		pageServ.updateCategory(oldCategoryName, newCategoryName);
	}

	public List<DefaultApprovalLineDTO> getApprovalLines(String doc_type) {
		return defaultServ.getApprovalLines(doc_type);
	}

	public void saveApprovalLines(List<DefaultApprovalLineDTO> lines, String doc_type, Long drafter_rank_seq) {
		defaultServ.saveApprovalLines(lines, doc_type, drafter_rank_seq);
	}

	public void deleteApprovalLine(String doc_type, Long drafter_rank_seq) {
		defaultServ.deleteApprovalLine(doc_type, drafter_rank_seq);
	}

	//연차관리
	public Map<String, Object> getAllLeaveList(String keyword, Long cPage) {
	    return annualLeaveServ.getAllLeaveList(keyword, cPage);
	}
	
	public Map<String, Object> updateAdminLeave(AdminLeaveDTO dto) {
	    return annualLeaveServ.updateAdminLeave(dto);
	}
	
	public List<CertIssueRequestDTO> getAdminCertRequestList() {
		return certServ.getAdminCertRequestList();
	}
	
	public void approveCertRequest(Long cert_request_seq, String loginId) {
		certServ.approveCertRequest(cert_request_seq, loginId);
	}
	
	public void rejectCertRequest(Long cert_request_seq, String reject_reason, String loginId) {
		certServ.rejectCertRequest(cert_request_seq, reject_reason, loginId);
	}

	public List<CertTypeDTO> getAdminCertTypeList() {
		return certServ.getAdminCertTypeList();
	}
	
	public void updateCertTypeHidden(CertTypeDTO dto) {
		certServ.updateCertTypeHidden(dto);
	}
	
	public void updateCertType(CertTypeDTO dto) {
		certServ.updateCertType(dto);
	}
	
	public List<CertIssueHistoryDTO> getCertIssueHistoryList() {
		return certServ.getCertIssueHistoryList();
	}

    @Autowired
    private UsersRoleService usersRoleServ;
	
	public boolean isHrAuthorized(String loginId) {   // ← 이 메서드가 있어야 함
        return usersRoleServ.isHrAuthorized(loginId);
    }
	
	//직원 관리 - 직원 등록  
	public void registerUserByAdmin(UsersDTO dto, MultipartFile profile) {
        usersServ.registerUserByAdmin(dto,profile); 
	}
}
