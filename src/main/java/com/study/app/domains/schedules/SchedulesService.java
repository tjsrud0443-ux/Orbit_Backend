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
	
	public int deleteSchedules(Long schedule_seq) {
		return schedDAO.deleteSchedules(schedule_seq);
	}
	
	public int updateSchedules(SchedulesDTO dto) {
		return schedDAO.updateSchedules(dto);
	}
}
