package com.study.app.domains.schedules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.meetingRooms.RoomRsvnDTO;
import com.study.app.domains.projects.ProjectMembersDTO;
import com.study.app.domains.projects.ProjectsDTO;

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
	
	public void deleteSchedule(Long rsvn_seq, String users_id) {
		schedDAO.deleteMeetingSchedule(rsvn_seq, users_id);
	}
	
	public void insertMeetAddMember(RoomRsvnDTO rsvn, String users_id) {
		SchedulesDTO dto = new SchedulesDTO();
		dto.setTitle(rsvn.getTitle());
		dto.setUsers_id(users_id);
		dto.setStart_dt(rsvn.getStart_dt());
		dto.setEnd_dt(rsvn.getEnd_dt());
		dto.setSked_reason(rsvn.getTitle());
		dto.setRef_seq(rsvn.getRsvn_seq());
		
		schedDAO.insertMeetAddMember(dto);
	}
	
	public void updateMeetSchedule(RoomRsvnDTO dto) {
		schedDAO.updateMeetSchedule(dto);
	}
	
	public void cancelMeetRsvn(Long rsvn_seq) {
		schedDAO.cancelMeetRsvn(rsvn_seq);
	}
	
	public void insertMyProjectSchedule(String loginId, ProjectsDTO dto) {
		SchedulesDTO schedDto = new SchedulesDTO();
		schedDto.setTitle(dto.getProject_name());
		schedDto.setUsers_id(loginId);
		schedDto.setStart_dt(dto.getStart_date());
		schedDto.setEnd_dt(dto.getEnd_date());
		schedDto.setSked_reason(dto.getProject_name());
		schedDto.setRef_seq(dto.getProject_seq());
		schedDAO.insertProjectSchedule(schedDto);
	}
	
	public void insertProjectMemberSchedule(Long project_seq, ProjectsDTO dto, ProjectMembersDTO memberDto) {
		SchedulesDTO schedDto = new SchedulesDTO();
		schedDto.setTitle(dto.getProject_name());
		schedDto.setUsers_id(memberDto.getUsers_id());
		schedDto.setStart_dt(dto.getStart_date());
		schedDto.setEnd_dt(dto.getEnd_date());
		schedDto.setSked_reason(dto.getProject_name());
		schedDto.setRef_seq(project_seq);
		schedDAO.insertProjectSchedule(schedDto);
	}
}
