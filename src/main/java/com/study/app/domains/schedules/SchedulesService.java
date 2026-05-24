package com.study.app.domains.schedules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulesService {
	
	@Autowired
	private SchedulesDAO schedDAO;
	
	public int insertSchedules(SchedulesDTO dto) {
		return schedDAO.insertSchedules(dto);
	}
	
	public List<SchedulesDTO> getSchedules(String usersId) {
	    return schedDAO.getSchedules(usersId);
	}
}
