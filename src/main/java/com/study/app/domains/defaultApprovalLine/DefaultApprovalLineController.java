package com.study.app.domains.defaultApprovalLine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/defaultLine")
public class DefaultApprovalLineController {
	
	@Autowired
	private DefaultApprovalLineService defaultServ;
}
