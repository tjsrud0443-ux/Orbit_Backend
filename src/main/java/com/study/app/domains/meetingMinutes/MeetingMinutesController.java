package com.study.app.domains.meetingMinutes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
		dto.setUsersId(loginId);
		minutesServ.insertMinutes(dto);
		return ResponseEntity.ok().build();
	}
}
