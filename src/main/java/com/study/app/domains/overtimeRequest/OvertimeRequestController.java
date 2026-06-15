package com.study.app.domains.overtimeRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/overtimeRQ")
public class OvertimeRequestController {
	
	@Autowired
	private OvertimeRequestService overtimeServ;
	
	@PostMapping("/insert")
	public ResponseEntity<Void> insertOvertimeReq(
	        @RequestAttribute String loginId,
	        @RequestBody OvertimeRequestDTO dto) {
	    overtimeServ.insertOvertimeReq(loginId, dto);
	    return ResponseEntity.ok().build();
	}
}
