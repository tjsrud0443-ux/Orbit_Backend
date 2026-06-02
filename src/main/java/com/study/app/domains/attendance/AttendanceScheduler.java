package com.study.app.domains.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AttendanceScheduler {

	@Autowired
	private AttendanceService attendServ;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void autoCheckOut() {
		attendServ.autoAttendanceCheck();
	}
}
