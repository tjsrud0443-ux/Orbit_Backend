package com.study.app.domains.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {
	
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
	
	@PutMapping("/myPage/edit")
	public ResponseEntity<Void> updateMyPageInfo(@RequestAttribute String loginId,@RequestBody UsersDTO dto){
		dto.setId(loginId);
		usersServ.updateMyPageInfo(dto);
		return ResponseEntity.ok().build();
	}
}
