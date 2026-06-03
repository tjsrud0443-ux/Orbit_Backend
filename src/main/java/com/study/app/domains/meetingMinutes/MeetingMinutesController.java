package com.study.app.domains.meetingMinutes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/minutes")
public class MeetingMinutesController {

	@Autowired
	private MeetingMinutesService minutesServ;

	@PostMapping
	public ResponseEntity<Void> insertMinutes(@RequestAttribute String loginId, @RequestBody MeetingMinutesDTO dto){
		dto.setUsers_id(loginId);
		minutesServ.insertMinutes(dto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/minutesList")
	public ResponseEntity<List<MeetingMinutesDTO>> getMinutesList(){
		List<MeetingMinutesDTO> list = minutesServ.getMinutesList();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/detail/{minute_seq}")
	public ResponseEntity<MeetingMinutesDTO> getMinutesDetail(@PathVariable Long minute_seq){
		MeetingMinutesDTO list = minutesServ.getMinutesDetail(minute_seq);
		return ResponseEntity.ok(list);
	}
}
