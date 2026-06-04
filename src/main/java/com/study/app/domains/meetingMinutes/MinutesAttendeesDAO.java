package com.study.app.domains.meetingMinutes;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MinutesAttendeesDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertMinutesAttendees(MinutesAttendeesDTO dto) {
		return mybatis.insert("MinutesAttendees.insertMinutesAttendees",dto);
	}
	
	public int deleteMinutesAttendees(Long minute_seq) {
		return mybatis.delete("MinutesAttendees.deleteMinutesAttendees",minute_seq);
	}
}
