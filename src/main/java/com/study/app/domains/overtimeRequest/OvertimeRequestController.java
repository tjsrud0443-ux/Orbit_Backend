package com.study.app.domains.overtimeRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/overtimeRQ")
public class OvertimeRequestController {
	
	@Autowired
	private OvertimeRequestService overtimeServ;
	
	
}
