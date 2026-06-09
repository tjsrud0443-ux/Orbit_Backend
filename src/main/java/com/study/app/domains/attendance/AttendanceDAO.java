package com.study.app.domains.attendance;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	
	public int checkOut(AttendanceDTO dto) {
		return mybatis.update("Attendance.checkOut",dto);
	}
	
	public AttendanceDTO getCntMonth(String loginId) {
		return mybatis.selectOne("Attendance.getCntMonth",loginId);
	}
	
	public AttendanceDTO getCntWeek(String loginId) {
		return mybatis.selectOne("Attendance.getCntWeek",loginId);
	}
	

}
