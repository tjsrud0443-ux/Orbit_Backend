package com.study.app.domains.meetingRooms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meetingRooms")
public class MeetingRoomsController {
	
	@Autowired
	private MeetingRoomsService roomServ;
	
	@GetMapping("getAllRooms")
	public ResponseEntity<List<MeetingRoomsDTO>> getAllRooms(){
		List<MeetingRoomsDTO> list = roomServ.getAllRooms();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("createReservation")
	public ResponseEntity<?> createReservation(@RequestBody RoomRsvnDTO dto,
												@RequestAttribute String loginId) {
		
		try {
			roomServ.createReservation(dto, loginId);
			return ResponseEntity.ok().build();
		} catch (RuntimeException e) {
			if ("CONFLICT".equals(e.getMessage())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT)
	                                 .body(Map.of("message", "CONFLICT"));
			}
			e.printStackTrace();
		    System.out.println("에러 메세지: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@GetMapping("getReservations")
	public ResponseEntity<List<RoomRsvnDTO>> getReservations(@RequestParam String date,
															 @RequestParam Long room_seq) {
		
		List<RoomRsvnDTO> list = roomServ.getReservations(date, room_seq);
		return ResponseEntity.ok(list);
	}
}
