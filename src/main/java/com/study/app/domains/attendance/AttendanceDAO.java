package com.study.app.domains.attendance;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.app.domains.checkoutRequest.CheckoutRequestDTO;
import com.study.app.domains.overtimeRequest.OvertimeRequestDTO;

@Repository
public class AttendanceDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int checkIn(AttendanceDTO dto) {
		return mybatis.insert("Attendance.checkIn",dto);
	}
	
	public AttendanceDTO getAttendance(String users_id) {
		return mybatis.selectOne("Attendance.getAttendance",users_id);
	}
	
	public List<AttendanceDTO> getMyAttendanceList(String usersId) {
	    return mybatis.selectList("Attendance.getMyAttendanceList", usersId);
	}
	
	public int checkOut(AttendanceDTO dto) {
		return mybatis.update("Attendance.checkOut",dto);
	}
	
	public AttendanceDTO getCntMonth(String loginId) {
		return mybatis.selectOne("Attendance.getCntMonth",loginId);
	}
	
	public AttendanceDTO getCntWeek(String loginId) {
		return mybatis.selectOne("Attendance.getCntWeek",loginId);
	}

	public void changeCheckout(CheckoutRequestDTO dto) {
		mybatis.update("Attendance.changeCheckout", dto);
	}
	
	public void updateOvertime(OvertimeRequestDTO dto) {
		mybatis.update("Attendance.updateOvertime", dto);
	}
}
