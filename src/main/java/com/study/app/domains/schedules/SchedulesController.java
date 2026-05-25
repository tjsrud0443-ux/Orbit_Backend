package com.study.app.domains.schedules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.util.JWTUtil;

@RestController
@RequestMapping("/Schedules")
public class SchedulesController {
	@Autowired
	private SchedulesService schedServ;
	
	@Autowired
	private JWTUtil jwtUtil;  // JWT 유틸 주입
	
	@PostMapping
	public ResponseEntity<Void> insertSchedules(@RequestBody SchedulesDTO dto,
												@RequestHeader("Authorization") String token){
		String usersId = jwtUtil.getSubject(token.replace("Bearer ", ""));
        dto.setUsers_id(usersId);
		schedServ.insertSchedules(dto);
		return ResponseEntity.ok().build();
	}
	
	
	@GetMapping
	public ResponseEntity<List<SchedulesDTO>> getSchedules(
	    @RequestHeader("Authorization") String token) {
	    
	    String usersId = jwtUtil.getSubject(token.replace("Bearer ", ""));
	    List<SchedulesDTO> list = schedServ.getSchedules(usersId);
	    return ResponseEntity.ok(list);
	}
	
	@DeleteMapping("/{schedule_seq}")
	public ResponseEntity<Void> deleteSchedules(@PathVariable Long schedule_seq){
		schedServ.deleteSchedules(schedule_seq);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{schedule_seq}")
	public ResponseEntity<Void> updateSchedules(@PathVariable Long schedule_seq, @RequestBody SchedulesDTO dto){
		dto.setSchedule_seq(schedule_seq);
		schedServ.updateSchedules(dto);
		return ResponseEntity.ok().build();
	}
}
