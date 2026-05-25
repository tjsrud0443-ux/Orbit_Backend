package com.study.app.domains.rank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rank")
public class RankController {
	
	@Autowired
	private RankService rankServ;
}
