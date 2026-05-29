package com.study.app.domains.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.util.JWTUtil;

@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private JWTUtil jwt;
	@Autowired
	private AuthService authServ;

	@PostMapping("login")
	public ResponseEntity<Map<String, String>> login(@RequestBody AuthDTO dto) {
		int result = authServ.login(dto);

		if(result == 1) {
			Map<String, String> response = new HashMap<>();
			String token = jwt.createToken(dto.getId());
			response.put("token", token);
			response.put("id", dto.getId());
			return ResponseEntity.ok(response);
		}
		
		if(result == -1) { // 탈퇴, 휴직 403 리턴
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}
}
