package com.study.app.domains.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	
	@Autowired
	private AdminService adminServ;
	@Autowired
	private SignupService signupServ;
	@Autowired
	private UsersService userServ;
	
	@GetMapping("allRequest")
    public ResponseEntity<Map<String, Object>> getAllRequest(@RequestParam Long cPage,
    															@RequestParam String status,
    															@RequestParam(required = false) String searchTerm) {
        Map<String, Object> result = signupServ.getAllRequest(cPage, status, searchTerm);
        return ResponseEntity.ok(result);
    }
	
	@GetMapping("/{signup_seq}")
	public ResponseEntity<SignupDTO> getUserInfo(@PathVariable Long signup_seq){
		SignupDTO dto = signupServ.getUserInfo(signup_seq);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("getHrInfo")
	public ResponseEntity<UsersDTO> getHrInfo(@RequestParam String id){
		UsersDTO dto = userServ.getHrInfo(id);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("getDeptList")
	public ResponseEntity<List<DepartmentsDTO>> getDeptList(){
		List<DepartmentsDTO> dept_list = adminServ.getDeptList();
		return ResponseEntity.ok(dept_list);
	}
	
	@GetMapping("getRankList")
	public ResponseEntity<List<RankDTO>> getRankList(){
		List<RankDTO> rank_list = adminServ.getRankList();
		return ResponseEntity.ok(rank_list);
	}
	
	@PostMapping("userSignup")
	public ResponseEntity<Void> userSignup(@RequestBody SignupRequestDTO dto){
		adminServ.userSignup(dto);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("rejectSignup")
	public ResponseEntity<Void> rejectSignup(@RequestParam Long signup_seq){
		signupServ.rejectSignup(signup_seq);
		return ResponseEntity.ok().build();
	}
}
