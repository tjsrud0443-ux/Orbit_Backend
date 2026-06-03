package com.study.app.domains.meetingMinutes;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MeetingMinutesDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertMinutes(MeetingMinutesDTO dto) {
		return mybatis.insert("MeetingMinutes.insertMinutes",dto);
	}
}
