package com.study.app.domains.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.domains.annualLeave.AnnualLeaveDTO;

@RestController
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	private MypageService mypageServ;
	
	@GetMapping("/annualSummary")
	public ResponseEntity<AnnualLeaveDTO> getAnnualLeaveSummary(@RequestAttribute String loginId){
		AnnualLeaveDTO annualSummary = mypageServ.getAnnualLeaveSummary(loginId);
		return ResponseEntity.ok(annualSummary);
	}
}
