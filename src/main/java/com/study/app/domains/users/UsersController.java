package com.study.app.domains.users;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	private static final Logger log = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	private UsersService usersServ;
	
	@GetMapping("/info")
	public ResponseEntity<UsersDTO> getUsersInfo(@RequestAttribute String loginId) {
		UsersDTO result = usersServ.getUsersInfo(loginId);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/myPage")
	public ResponseEntity<UsersDTO> getMyPageInfo(@RequestAttribute String loginId){
		UsersDTO result = usersServ.getMyPageInfo(loginId);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/myPage/checkEmail")
	public ResponseEntity<Boolean> checkDupEmail(
	    @RequestAttribute String loginId,
	    @RequestParam String email) {

	    return ResponseEntity.ok(usersServ.checkDupEmail(email, loginId));
	}
	
	@PutMapping("/myPage/edit")
	public ResponseEntity<Void> updateMyPageInfo(@RequestAttribute String loginId,@RequestBody UsersDTO dto){
		dto.setId(loginId);
		usersServ.updateMyPageInfo(dto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/myPage/stamp")
	public ResponseEntity<Map<String, String>> uploadStamp(
	        @RequestAttribute String loginId,
	        @RequestParam("file") MultipartFile file) throws Exception {

	    Map<String, String> uploadResult = usersServ.uploadUserStamp(loginId, file);
	    return ResponseEntity.ok(uploadResult);
	}
	
	@PutMapping("/myPage/updateProfileFile")
	public ResponseEntity<Map<String, String>> updateProfileFile(@RequestAttribute String loginId,
			@RequestParam("file") MultipartFile file) throws Exception{
		
		Map<String, String> result = usersServ.updateProfileFile(loginId, file);
		return ResponseEntity.ok(result);
	}
}
