package com.study.app.domains.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.util.JWTUtil;

@RestController
@RequestMapping("/Attendance")
public class AttendanceController {
	@Autowired
	private AttendanceService attendServ;
	
	@Autowired
	private JWTUtil jwtUtil;  // JWT 유틸 주입
	
	@PostMapping("/checkIn")
	public ResponseEntity<?> checkIn(@RequestHeader("Authorization") String token){
		try {
			String usersId = jwtUtil.getSubject(token.replace("Bearer ", ""));
			AttendanceDTO dto = new AttendanceDTO();
	        dto.setUsers_id(usersId);
			attendServ.checkIn(dto);
			return ResponseEntity.ok().build();
		} catch (RuntimeException e) {
	        return ResponseEntity.status(400).body(e.getMessage());
	    }
	}
	
	@GetMapping("/status")
	public ResponseEntity<AttendanceDTO> getAttendance(@RequestHeader("Authorization") String token){
		String usersId = jwtUtil.getSubject(token.replace("Bearer ", ""));
		AttendanceDTO attendance = attendServ.getAttendance(usersId);
		if (attendance == null) {
	        return ResponseEntity.ok(new AttendanceDTO()); // ✅ 빈 DTO 반환
	    }
	    return ResponseEntity.ok(attendance);
	}
	
	@PutMapping("/checkOut")
	public ResponseEntity<Void> checkOut(@RequestHeader("Authorization") String token){
		String usersId = jwtUtil.getSubject(token.replace("Bearer ", ""));
		AttendanceDTO dto = new AttendanceDTO();
		dto.setUsers_id(usersId); 
		attendServ.checkOut(dto);
		return ResponseEntity.ok().build();
	}
}
