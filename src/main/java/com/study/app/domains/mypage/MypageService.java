package com.study.app.domains.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.annualLeave.AnnualLeaveDAO;
import com.study.app.domains.annualLeave.AnnualLeaveDTO;

@Service
public class MypageService {
	
	@Autowired
	private AnnualLeaveDAO annualDAO;
	
	public AnnualLeaveDTO getAnnualLeaveSummary(String loginId) {
		return annualDAO.getAnnualLeaveSummary(loginId);
	}
}
