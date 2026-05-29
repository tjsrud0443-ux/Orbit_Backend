package com.study.app.domains.admin;

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
import org.springframework.web.bind.annotation.RestController;

import com.study.app.domains.departments.DepartmentsDTO;
import com.study.app.domains.rank.RankDTO;
import com.study.app.domains.signup.SignupDTO;
import com.study.app.domains.signup.SignupRequestDTO;
import com.study.app.domains.signup.SignupService;
import com.study.app.domains.users.UsersDTO;
import com.study.app.domains.users.UsersService;

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
	private SignupService signupServ;
	@Autowired
	private UsersService userServ;
	
	@GetMapping("/hr/allRequest")
    public ResponseEntity<Map<String, Object>> getAllRequest(@RequestParam Long cPage,
    															@RequestParam String status,
    															@RequestParam(required = false) String searchTerm) {
        Map<String, Object> result = signupServ.getAllRequest(cPage, status, searchTerm);
        return ResponseEntity.ok(result);
    }
	
	@GetMapping("/hr/{signup_seq}")
	public ResponseEntity<SignupDTO> getUserInfo(@PathVariable Long signup_seq){
		SignupDTO dto = signupServ.getUserInfo(signup_seq);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/hr/getHrInfo")
	public ResponseEntity<UsersDTO> getHrInfo(@RequestParam String id){
		UsersDTO dto = userServ.getHrInfo(id);
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
		signupServ.rejectSignup(signup_seq);
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
	
	
}
