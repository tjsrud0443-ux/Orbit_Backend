package com.study.app.domains.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.aiChat.AiUnansweredQuestionsDTO;
import com.study.app.domains.annualLeave.AdminLeaveDTO;
import com.study.app.domains.certType.CertIssueHistoryDTO;
import com.study.app.domains.certType.CertIssueRequestDTO;
import com.study.app.domains.certType.CertTypeDTO;
import com.study.app.domains.companyInfo.CompanyInfoDTO;
import com.study.app.domains.defaultApprovalLine.DefaultApprovalLineDTO;
import com.study.app.domains.departments.DepartmentsCountDTO;
import com.study.app.domains.departments.DepartmentsDTO;
import com.study.app.domains.departments.DeptLeaveDTO;
import com.study.app.domains.documents.DocumentsDTO;
import com.study.app.domains.documents.DocumentsService;
import com.study.app.domains.meetingRooms.MeetingRoomsDTO;
import com.study.app.domains.pageInfo.PageInfoDTO;
import com.study.app.domains.rank.RankDTO;
import com.study.app.domains.signup.SignupDTO;
import com.study.app.domains.signup.SignupRequestDTO;
import com.study.app.domains.supplies.SupplyDTO;
import com.study.app.domains.supplies.SupplyRentalDTO;
import com.study.app.domains.supplies.SupplyRequestDTO;
import com.study.app.domains.supplies.SupplyService;
import com.study.app.domains.users.UsersDTO;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminServ;
	@Autowired
	private DocumentsService docServ;
	@Autowired
	private SupplyService supplyServ;

	@GetMapping("/hr/allRequest")
	public ResponseEntity<Map<String, Object>> getAllRequest(@RequestParam Long cPage,
			@RequestParam String status,
			@RequestParam(required = false) String searchTerm) {
		Map<String, Object> result = adminServ.getAllRequest(cPage, status, searchTerm);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/hr/{signup_seq}")
	public ResponseEntity<SignupDTO> getUserInfo(@PathVariable Long signup_seq){
		SignupDTO dto = adminServ.getUserInfo(signup_seq);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/hr/getHrInfo")
	public ResponseEntity<UsersDTO> getHrInfo(@RequestParam String id){
		UsersDTO dto = adminServ.getHrInfo(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/hr/getDeptList")
	public ResponseEntity<List<DepartmentsDTO>> getDeptList(){
		List<DepartmentsDTO> dept_list = adminServ.getDeptList();
		return ResponseEntity.ok(dept_list);
	}

	@GetMapping("/hr/getRankList")
	public ResponseEntity<List<RankDTO>> getRankList(){
		List<RankDTO> rank_list = adminServ.getRankList();
		return ResponseEntity.ok(rank_list);
	}

	@PostMapping("/hr/userSignup")
	public ResponseEntity<Void> userSignup(@RequestBody SignupRequestDTO dto){
		adminServ.userSignup(dto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/hr/rejectSignup")
	public ResponseEntity<Void> rejectSignup(@RequestParam Long signup_seq){
		adminServ.rejectSignup(signup_seq);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/hr/getAllUsers")
	public ResponseEntity<Map<String, Object>> getAllUsers(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String status,
			@RequestParam(defaultValue = "1") Long page){

		Long start = page * 10 - 9;
		Long end = page * 10;

		Map<String, Object> result = adminServ.getAllUsers(keyword,status, start, end);

		return ResponseEntity.ok(result);
	}

	@PutMapping("/hr/updateUsersState")
	public ResponseEntity<Void> updateUsersState(@RequestBody UsersDTO dto){
		adminServ.updateUsersState(dto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/hr/updateUsersInfo")
	public ResponseEntity<Void> updateUsersInfo(@RequestBody UsersDTO dto){
		adminServ.updateUsersInfo(dto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/hr/addDept")
	public ResponseEntity<Void> addDept(@RequestBody DepartmentsDTO dto) {
		adminServ.addDept(dto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/hr/delDept/{dept_seq}")
	public ResponseEntity<Void> deleteDept(@PathVariable Long dept_seq) {
		adminServ.deleteDept(dept_seq);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/hr/updateDept")
	public ResponseEntity<Void> updateDept(@RequestBody DepartmentsDTO dto) {
		adminServ.updateDept(dto);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/dashboard")
	public ResponseEntity<Map<String, Object>> getDashboard() {
		Map<String, Object> result = adminServ.getDashboard();

		return ResponseEntity.ok(result);
	}

	@GetMapping("/deptEmployeeCount")
	public ResponseEntity<List<DepartmentsCountDTO>> deptEmployeeCount() {
		return ResponseEntity.ok(adminServ.deptEmployeeCount());
	}

	@GetMapping("/deptLeave")
	public ResponseEntity<List<DeptLeaveDTO>> getDeptLeave() {
		return ResponseEntity.ok(adminServ.getDeptLeave()); 
	}
	
	@GetMapping("/joinResign")
	public ResponseEntity<Map<String, Object>> joinResignCount() {
		Map<String, Object> result = adminServ.joinResignCount(); 
		return ResponseEntity.ok(result); 
	}

	@GetMapping("getAllDocs")
	public ResponseEntity<List<DocumentsDTO>> getAllDocs(){
		List<DocumentsDTO> resp = adminServ.getAllDocs();
		return ResponseEntity.ok(resp);
	}
	
	@PostMapping("addDocument")
	public ResponseEntity<Void> addDoc(@RequestParam String title,
										 @RequestParam String users_id,
										 @RequestParam MultipartFile file) {
	    
		docServ.addDoc(title, users_id, file);
	    return ResponseEntity.ok().build();
	}
	
	@PutMapping("editDocument")
    public ResponseEntity<Void> editDocument(
    		@RequestParam Long document_seq,
            @RequestParam String title,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        docServ.editDoc(document_seq, title, file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deleteDocument/{document_seq}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long document_seq) {
        docServ.deleteDoc(document_seq);
        return ResponseEntity.ok().build();
    }
	
	@GetMapping("ga/getAllRooms")
	public ResponseEntity<List<MeetingRoomsDTO>> getAllRooms() {
		List<MeetingRoomsDTO> result = adminServ.getAllRooms();
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("ga/addMeetingRoom")
	public ResponseEntity<Void> addMeetingRoom(@RequestPart("input") MeetingRoomsDTO dto,
												@RequestPart("file") MultipartFile file){
		
		adminServ.addMeetingRoom(dto, file);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("ga/editMeetingRoom")
	public ResponseEntity<Void> editMeetingRoom(@RequestPart("input") MeetingRoomsDTO dto,
												@RequestPart(value = "file", required = false) MultipartFile file){
		
		adminServ.editMeetingRoom(dto, file);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("ga/deleteMeetingRoom/{room_seq}")
	public ResponseEntity<Void> deleteMeetingRoom(@PathVariable Long room_seq){
		adminServ.deleteMeetingRoom(room_seq);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/aiQuestions")
	public ResponseEntity<List<AiUnansweredQuestionsDTO>> getAiQuestions() {
		return ResponseEntity.ok(adminServ.getAiQuestions());
	}
	
	@GetMapping("/ai/myDeptQuestion")
	public ResponseEntity<List<AiUnansweredQuestionsDTO>> myDeptQuestion(
			@RequestParam Long dept_seq, @RequestParam boolean is_super_admin) {
		return ResponseEntity.ok(adminServ.myDeptQuestion(dept_seq, is_super_admin));
	}
	
	@PutMapping("/ai/insertUpdateAnswer")
	public ResponseEntity<Void> insertUpdateAnswer(
			@RequestBody AiUnansweredQuestionsDTO dto) {
		adminServ.insertUpdateAnswer(dto);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/ai/deleteAnswer/{question_seq}")
	public ResponseEntity<Void> deleteAnswer(@PathVariable Long question_seq) {
		adminServ.deleteAnswer(question_seq);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/ai/adminAiQuestionsData")
	public ResponseEntity<AiQuestionCountDTO> adminAiQuestionsData(
			@RequestParam Long dept_seq, @RequestParam boolean is_super_admin) {
		return ResponseEntity.ok(adminServ.adminAiQuestionsData(dept_seq, is_super_admin));
	}
	
	/*비품 관련*/
	@GetMapping("/supply")
	public ResponseEntity<List<SupplyDTO>> getSupplyList(){
		List<SupplyDTO> supplies = adminServ.getSupplyList();
		return ResponseEntity.ok(supplies);
	}
	
	@PostMapping("/supplyInsert")
	public ResponseEntity<?> insertSupply(@RequestBody SupplyDTO dto){
		try {
	        adminServ.insertSupply(dto);
	        return ResponseEntity.ok().build();
		    } catch (RuntimeException e) {
		        return ResponseEntity.badRequest().body(e.getMessage());  // 400 응답
		    }
	}
	
	@PutMapping("/supplyUpdate")
	public ResponseEntity<Void> updateSupplies(@RequestBody SupplyDTO dto) {
		adminServ.updateSupplies(dto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/supplyDelete")
	public ResponseEntity<Void> deleteSupplies(@RequestBody Map<String, List<Long>> body) {
	    adminServ.deleteSupplies(body.get("ids"));
	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("/supplyReq")
	public ResponseEntity<Map<String, Object>> getAdminRequestList(
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "8") int size,
	        @RequestParam(defaultValue = "") String keyword,
	        @RequestParam(defaultValue = "") String status) {

	    Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
	    params.put("status", status);
	    params.put("start", (page - 1) * size + 1);
	    params.put("end", page * size);

	    List<SupplyRequestDTO> list = adminServ.getAdminRequestList(params);
	    int total = adminServ.getAdminRequestCount(params);

	    // 탭 카운트용 - keyword는 유지, status만 바꿔서 각각 카운트
	    Map<String, Object> countParams = new HashMap<>();
	    countParams.put("keyword", "");

	    countParams.put("status", "");
	    int totalCount = adminServ.getAdminRequestCount(countParams);

	    countParams.put("status", "PENDING");
	    int pendingCount = adminServ.getAdminRequestCount(countParams);

	    countParams.put("status", "APPROVED");
	    int approvedCount = adminServ.getAdminRequestCount(countParams);

	    countParams.put("status", "REJECTED");
	    int rejectedCount = adminServ.getAdminRequestCount(countParams);

	    Map<String, Object> result = new HashMap<>();
	    result.put("list", list);
	    result.put("total", total);
	    result.put("totalPages", (int) Math.ceil((double) total / size));
	    result.put("totalCount", totalCount);
	    result.put("pendingCount", pendingCount);
	    result.put("approvedCount", approvedCount);
	    result.put("rejectedCount", rejectedCount);
	    
	    return ResponseEntity.ok(result);
	}
	
	@PutMapping("/supplyReqStatus")
	public ResponseEntity<?> supplyReqStatus(@RequestBody SupplyRequestDTO srDto) {
		adminServ.approveRequest(srDto);
	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("/supplyRental")
	public ResponseEntity<Map<String, Object>> supplyRentalList(
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "8") int size,
	        @RequestParam(defaultValue = "") String keyword,
	        @RequestParam(defaultValue = "") String status) {

	    Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
	    params.put("status", status);
	    params.put("start", (page - 1) * size + 1);
	    params.put("end", page * size);

	    List<SupplyRentalDTO> list = adminServ.supplyRentalList(params);
	    
	    // 탭 카운트용
	    Map<String, Object> countParams = new HashMap<>();
//	    countParams.put("keyword", keyword);

	    countParams.put("status", "");
	    int totalCount = adminServ.supplyRentalCount(countParams);

	    countParams.put("status", "RENTING");
	    int rentingCount = adminServ.supplyRentalCount(countParams);

	    countParams.put("status", "RETURNED");
	    int returnedCount = adminServ.supplyRentalCount(countParams);

	    int currentCount;
	    if (status.isEmpty()) {
	        currentCount = totalCount;
	    } else if (status.equals("RENTING")) {
	        currentCount = rentingCount;
	    } else {
	        currentCount = returnedCount;
	    }
	    
	    Map<String, Object> result = new HashMap<>();
	    result.put("list", list);
	    result.put("totalPages", (int) Math.ceil((double) currentCount / size));
	    result.put("totalCount", totalCount);
	    result.put("rentingCount", rentingCount);
	    result.put("returnedCount", returnedCount);

	    return ResponseEntity.ok(result);
	}
	
	@PutMapping("/returnSupply")
	public ResponseEntity<?> returnSupply(@RequestBody SupplyRentalDTO dto) {
	    supplyServ.returnSupply(dto);
	    return ResponseEntity.ok().build();
	}

	@GetMapping("hr/getAllCheckoutRQ")
	public ResponseEntity<Map<String, Object>> getAllCheckoutRQ(@RequestParam Long cPage,
															 @RequestParam String status) {
		
		Map<String, Object> result = adminServ.getAllCheckoutRQ(cPage, status);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("hr/getAllOvertimeRQ")
	public ResponseEntity<Map<String, Object>> getAllOvertimeRQ(@RequestParam Long cPage,
															 @RequestParam String status) {
		
		Map<String, Object> result = adminServ.getAllOvertimeRQ(cPage, status);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("hr/approveCheckout/{checkout_seq}")
	public ResponseEntity<Void> approveCheckout(@PathVariable Long checkout_seq,
												@RequestAttribute String loginId) {
		
		adminServ.approveCheckout(checkout_seq, loginId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("hr/rejectCheckout/{checkout_seq}")
	public ResponseEntity<Void> rejectCheckout(@PathVariable Long checkout_seq,
												@RequestAttribute String loginId) {
		
		adminServ.rejectCheckout(checkout_seq, loginId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("hr/approveOvertime/{overtime_seq}")
	public ResponseEntity<Void> approveOvertime(@PathVariable Long overtime_seq,
												@RequestAttribute String loginId) {
		
		adminServ.approveOvertime(overtime_seq, loginId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("hr/rejectOvertime/{overtime_seq}")
	public ResponseEntity<Void> rejectOvertime(@PathVariable Long overtime_seq,
												@RequestAttribute String loginId) {
		
		adminServ.rejectOvertime(overtime_seq, loginId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/company/getCompanyInfo")
	public ResponseEntity<CompanyInfoDTO> getCompanyInfo() {
		return ResponseEntity.ok(adminServ.getCompanyInfo());
	}
	
	@PostMapping("/company/insertCompanyInfo")
	public ResponseEntity<Void> insertCompanyInfo(@RequestBody CompanyInfoDTO dto) {
		adminServ.insertCompanyInfo(dto);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/company/updateCompanyInfo")
	public ResponseEntity<Void> updateCompanyInfo(@RequestBody CompanyInfoDTO dto,
									@RequestAttribute String loginId) {
		adminServ.updateCompanyInfo(dto, loginId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/company/updateCompanyStamp")
	public ResponseEntity<Void> updateCompanyStamp(@RequestAttribute String loginId,
									@RequestParam("file") MultipartFile file) throws Exception{
		adminServ.updateCompanyStamp(loginId, file);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/company/updateCompanyWatermark")
	public ResponseEntity<Void> updateCompanyWatermark(@RequestAttribute String loginId,
									@RequestParam("file") MultipartFile file) throws Exception{
		adminServ.updateCompanyWatermark(loginId, file);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/hr/insertRank")
	public ResponseEntity<Void> insertRank(@RequestBody RankDTO dto) {
		adminServ.insertRank(dto);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/hr/updateRank")
	public ResponseEntity<Void> updateRank(@RequestBody RankDTO dto) {
		adminServ.updateRank(dto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/hr/deleteRank/{rank_seq}")
	public ResponseEntity<?> deleteRank(@PathVariable Long rank_seq) {
		try {
			adminServ.deleteRank(rank_seq);
			return ResponseEntity.ok().build();
		}catch(IllegalStateException e) {
			return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));
		}
	}
	
	@PutMapping("/hr/updateRankOrder")
	public ResponseEntity<Void> updateRankOrder(@RequestBody List<RankDTO> list) {
		adminServ.updateRankOrder(list);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("updatePageInfo/{page_seq}")
	public ResponseEntity<Void> updatePageInfo(@PathVariable Long page_seq,
												@RequestBody PageInfoDTO dto) {
		adminServ.updatePageInfo(page_seq, dto);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("updateCategory")
	public ResponseEntity<Void> updateCategory(@RequestBody Map<String, String> body) {
		String oldCategoryName = body.get("oldCategoryName");
	    String newCategoryName = body.get("editCategoryNewName");
	    adminServ.updateCategory(oldCategoryName, newCategoryName);
	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("defaultApprovalLine/list")
	public ResponseEntity<List<DefaultApprovalLineDTO>> getApprovalLines(@RequestParam String doc_type) {
		return ResponseEntity.ok(adminServ.getApprovalLines(doc_type));
	}
	
	@PostMapping("defaultApprovalLine/save")
	public ResponseEntity<Void> saveApprovalLines(@RequestBody List<DefaultApprovalLineDTO> lines,
													@RequestParam String doc_type,
													@RequestParam Long drafter_rank_seq) {
		
		adminServ.saveApprovalLines(lines, doc_type, drafter_rank_seq);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("defaultApprovalLine/delete")
	public ResponseEntity<Void> deleteApprovalLine(@RequestParam String doc_type,
													@RequestParam Long drafter_rank_seq) {
		
		adminServ.deleteApprovalLine(doc_type, drafter_rank_seq);
		return ResponseEntity.ok().build();
	}

	//연차관리
	@GetMapping("/hr/getAllLeaveList")
	public ResponseEntity<Map<String, Object>> getAllLeaveList(
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Long cPage) {
		Map<String, Object> result = adminServ.getAllLeaveList(keyword, cPage);
		return ResponseEntity.ok(result);
	}
	
	@PutMapping("/hr/updateUserLeave/{leaveSeq}")
	public ResponseEntity<?> updateAdminLeave(@PathVariable Long leaveSeq, @RequestBody AdminLeaveDTO dto) {
	    dto.setLeave_seq(leaveSeq);
	    Map<String, Object> result = adminServ.updateAdminLeave(dto);
	    return ResponseEntity.ok(result);
	}

	@GetMapping("/hr/getAdminCertRequestList")
	public ResponseEntity<List<CertIssueRequestDTO>> getAdminCertRequestList() {
		return ResponseEntity.ok(adminServ.getAdminCertRequestList());
	}
	
	@PutMapping("/hr/approveCertRequest/{cert_request_seq}")
	public ResponseEntity<Void> approveCertRequest(@PathVariable Long cert_request_seq,
													@RequestAttribute String loginId) {
		adminServ.approveCertRequest(cert_request_seq, loginId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/hr/rejectCertRequest/{cert_request_seq}")
	public ResponseEntity<Void> rejectCertRequest(@PathVariable Long cert_request_seq,
			@RequestBody CertIssueRequestDTO dto, @RequestAttribute String loginId) {
		adminServ.rejectCertRequest(cert_request_seq, dto.getReject_reason(), loginId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/hr/getAdminCertTypeList")
	public ResponseEntity<List<CertTypeDTO>> getAdminCertTypeList() {
		return ResponseEntity.ok(adminServ.getAdminCertTypeList());
	}
	
	@PutMapping("/hr/updateCertTypeHidden")
	public ResponseEntity<Void> updateCertTypeHidden(@RequestBody CertTypeDTO dto) {
		adminServ.updateCertTypeHidden(dto);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/hr/updateCertType")
	public ResponseEntity<Void> updateCertType(@RequestBody CertTypeDTO dto) {
		adminServ.updateCertType(dto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/hr/getCertIssueHistoryList")
	public ResponseEntity<List<CertIssueHistoryDTO>> getCertIssueHistoryList() {
		return ResponseEntity.ok(adminServ.getCertIssueHistoryList());
	}

	@PostMapping("/hr/registerUser")
    public ResponseEntity<Void> registerUserByAdmin(
            @RequestPart("input") UsersDTO dto,
            @RequestPart(value = "file", required = false) MultipartFile profile,
            @RequestAttribute String loginId) {

        if (!adminServ.isHrAuthorized(loginId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        adminServ.registerUserByAdmin(dto,profile);
        return ResponseEntity.ok().build();
    }
}
