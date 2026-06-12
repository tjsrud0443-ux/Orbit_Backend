package com.study.app.domains.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.aiChat.AiUnansweredQuestionsDTO;
import com.study.app.domains.departments.DepartmentsCountDTO;
import com.study.app.domains.departments.DepartmentsDTO;
import com.study.app.domains.departments.DeptLeaveDTO;
import com.study.app.domains.documents.DocumentsDTO;
import com.study.app.domains.documents.DocumentsService;
import com.study.app.domains.meetingRooms.MeetingRoomsDTO;
import com.study.app.domains.rank.RankDTO;
import com.study.app.domains.signup.SignupDTO;
import com.study.app.domains.signup.SignupRequestDTO;
import com.study.app.domains.supplies.SupplyDTO;
import com.study.app.domains.supplies.SupplyRequestDTO;
import com.study.app.domains.supplies.SupplyService;
import com.study.app.domains.users.UsersDTO;

@RestController
@RequestMapping("/admin")
public class AdminController {
	// 인사관련 mapping 주소 : /hr 로 시작할 것.
	// 총무관련 mapping 주소 : /ga 로 시작할 것.
	// 문서관리 : document 만 작성.
	// AI : ai 만 작성.

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
	public ResponseEntity<Void> delDept(@PathVariable Long dept_seq) {
		adminServ.delDept(dept_seq);
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
			@RequestParam Long dept_seq, @RequestParam String auth_group) {
		return ResponseEntity.ok(adminServ.myDeptQuestion(dept_seq, auth_group));
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
	
	/*비품 관련*/
	@GetMapping("/supply")
	public ResponseEntity<List<SupplyDTO>> getSupplyList(){
		List<SupplyDTO> supplies = adminServ.getSupplyList();
		return ResponseEntity.ok(supplies);
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
	    countParams.put("keyword", keyword);

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
	











































































































































































































































































































	@GetMapping("/hr/getAllCheckoutRQ")
	public ResponseEntity<Map<String, Object>> getAllCheckoutRQ(@RequestParam Long cPage,
															 @RequestParam String status) {
		
		Map<String, Object> result = adminServ.getAllCheckoutRQ(cPage, status);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/hr/getAllOvertimeRQ")
	public ResponseEntity<Map<String, Object>> getAllOvertimeRQ(@RequestParam Long cPage,
															 @RequestParam String status) {
		
		Map<String, Object> result = adminServ.getAllOvertimeRQ(cPage, status);
		return ResponseEntity.ok(result);
	}
	
}
