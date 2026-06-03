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
	
	private final MailService mailServ;
	public AuthController(MailService mailService) {
        this.mailServ = mailService;
    }

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
	
	// 아이디 찾기 인증번호 발송 요청
	@PostMapping("requestMailForId")
	public ResponseEntity<?> requestMailForId(@RequestBody Map<String, String> request) {
	    String name = request.get("name");
	    String email = request.get("email");
	    
	    boolean isSent = mailServ.isExistForId(name, email);
	    
	    if (isSent) {
	        return ResponseEntity.ok(Map.of("success", true, "message", "인증번호가 발송되었습니다."));
	    } else {
	        return ResponseEntity.badRequest().body(Map.of("success", false, "message", "입력하신 정보와 일치하는 회원이 없습니다."));
	    }
	}

	// 아이디 찾기
    @PostMapping("findId")
    public ResponseEntity<?> findId(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        
        String id = mailServ.findId(email, code);
        
        if (id != null) {
            return ResponseEntity.ok(Map.of("success", true, "userId", id));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "인증번호가 틀렸거나 만료되었습니다."));
        }
    }
    
	// 비밀번호 찾기 인증번호 발송 요청
	@PostMapping("requestMailForPw")
	public ResponseEntity<?> requestMailForPw(@RequestBody Map<String, String> request) {
	    String name = request.get("name");
	    String id = request.get("id");
	    String email = request.get("email");
	    
	    boolean isSent = mailServ.isExistForPw(name, id, email);
	    
	    if (isSent) {
	        return ResponseEntity.ok(Map.of("success", true, "message", "인증번호가 발송되었습니다."));
	    } else {
	        return ResponseEntity.badRequest().body(Map.of("success", false, "message", "입력하신 정보와 일치하는 회원이 없습니다."));
	    }
	}

    // 비밀번호 변경을 위한 토큰 발급
    @PostMapping("verifyForPw")
    public ResponseEntity<?> verifyForPw(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        
        String resetToken = mailServ.verifyForPw(email, code);
        
        if (resetToken != null) {
            return ResponseEntity.ok(Map.of("success", true, "resetToken", resetToken));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "인증번호가 틀렸거나 만료되었습니다."));
        }
    }

    // 비밀번호 변경
    @PostMapping("changePw")
    public ResponseEntity<?> changePw(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPw = request.get("newPw");
        String token = request.get("token");
        
        boolean isSuccess = mailServ.changePw(email, newPw, token);
        
        if (isSuccess) {
            return ResponseEntity.ok(Map.of("success", true, "message", "비밀번호가 성공적으로 변경되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "잘못된 접근이거나 만료된 요청입니다."));
        }
    }
}