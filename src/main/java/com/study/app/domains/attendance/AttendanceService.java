package com.study.app.domains.attendance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.checkoutRequest.CheckoutRequestDTO;
import com.study.app.domains.overtimeRequest.OvertimeRequestDTO;

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
	
	public List<AttendanceDTO> getMyAttendanceList(String usersId) {
	    return attendDAO.getMyAttendanceList(usersId);
	}
	
	public List<Map<String, Object>> getCheckInByMonth(String usersId, String yearMonth) {
	    return attendDAO.getCheckInByMonth(usersId, yearMonth);
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
	
	public void changeCheckout(CheckoutRequestDTO dto) {
		attendDAO.changeCheckout(dto);
	}
	
	public void updateOvertime(OvertimeRequestDTO dto) {
		attendDAO.updateOvertime(dto);
	}
}

