package com.study.app.domains.schedules;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SchedulesDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertSchedules(SchedulesDTO dto) {
		return mybatis.insert("Schedules.insertSchedules",dto);
	}
	
	public List<SchedulesDTO> getSchedules(String usersId) {
	    return mybatis.selectList("getSchedules", usersId);
	}
}
