package com.study.app.domains.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.domains.signup.SignupDTO;
import com.study.app.domains.signup.SignupService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminServ;
	@Autowired
	private SignupService signupServ;
	
	@GetMapping("allRequest")
    public ResponseEntity<Map<String, Object>> getAllRequest(@RequestParam Long cPage,
    															@RequestParam String status) {
        Map<String, Object> result = signupServ.getAllRequest(cPage, status);
        return ResponseEntity.ok(result);
    }
	
	@GetMapping("/{signup_seq}")
	public ResponseEntity<SignupDTO> getUserInfo(@PathVariable Long signup_seq){
		SignupDTO dto = signupServ.getUserInfo(signup_seq);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("getDeptList")
	public ResponseEntity<Map<String, Object>> getDeptList(){
		Map<String, Object> resp = adminServ.getDeptAndRank();
		return ResponseEntity.ok(resp);
	}
	
	@PostMapping
	public ResponseEntity
}
