package com.study.app.domains.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.file.FileService;

@RestController
@RequestMapping("signup")
public class SignupController {
	
	@Autowired
	private SignupService signupServ;
	
	@GetMapping("duplCheck")
	public ResponseEntity<Boolean> isExistId(@RequestParam String id){
		boolean result = signupServ.isExistId(id);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<Void> signupRequest(@RequestPart("input") SignupDTO dto,
												@RequestPart("file") MultipartFile profile) {
		
		signupServ.signupRequest(dto, profile);
		return ResponseEntity.ok().build();
	}
}
