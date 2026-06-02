package com.study.app.domains.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
	@Autowired
	private AttendanceDAO attendDAO;
	
	public void checkIn(AttendanceDTO dto) {
		 // 오늘 이미 출근 기록 있는지 확인
	    AttendanceDTO isDupAttend = attendDAO.getAttendance(dto.getUsers_id());
	    if (isDupAttend != null) {
	        throw new RuntimeException("이미 출근 처리되었습니다.");
	    }
		attendDAO.checkIn(dto);
	}
	
	public AttendanceDTO getAttendance(String users_id) {
		return attendDAO.getAttendance(users_id);
	}
	
	public int checkOut(AttendanceDTO dto) {
		return attendDAO.checkOut(dto);
	}
	
	public AttendanceDTO getCntMonth(String loginId) {
		return attendDAO.getCntMonth(loginId);
	}
	
	public AttendanceDTO getCntWeek(String loginId) {
		return attendDAO.getCntWeek(loginId);
	}
	
	public void autoAttendanceCheck() {
		attendDAO.autoAttendanceCheck();
	}
}

