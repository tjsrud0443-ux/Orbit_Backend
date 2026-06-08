package com.study.app.domains.annualLeave;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnnualLeaveScheduler {
	
	@Autowired
	private AnnualLeaveService annualServ;
	
	@Scheduled(cron = "0 5 0 * * *")
	public void deleteTempDoc() {
		annualServ.updateAnnualLeave();
	}
}
