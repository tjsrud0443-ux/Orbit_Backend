package com.study.app.domains.schedules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.meetingRooms.RoomRsvnDTO;

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
	
	public void addMeetingSchedules(RoomRsvnDTO rsvn, String loginId) {
		SchedulesDTO dto = new SchedulesDTO();
		dto.setTitle(rsvn.getTitle());
		dto.setUsers_id(loginId);
		dto.setStart_dt(rsvn.getStart_dt());
		dto.setEnd_dt(rsvn.getEnd_dt());
		dto.setSked_reason(rsvn.getTitle());
		dto.setRef_seq(rsvn.getRsvn_seq());
		
		schedDAO.addMeetingSchedules(dto);
	}
}
