package com.study.app.domains.schedules;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.meetingRooms.RoomRsvnDTO;
import com.study.app.domains.projects.ProjectMembersDTO;
import com.study.app.domains.projects.ProjectsDTO;
import com.study.app.domains.users.UsersDAO;
import com.study.app.domains.users.UsersDTO;

@Service
public class SchedulesService {
	
	@Autowired
	private SchedulesDAO schedDAO;
	@Autowired
	private UsersDAO usersDAO;
	
	public int insertSchedules(SchedulesDTO dto, String usersId) {
		Set<String> companyCategories = Set.of("COMPANY", "TEAM", "ANNIVERSARY", "holiday");
		if (companyCategories.contains(dto.getSchedule_type())) {
	        UsersDTO user = usersDAO.getUsersInfo(usersId); // 기존 유저 조회용 DAO/Mapper 활용
	        if (user == null || !"ROLE_HR_ADMIN".equals(user.getAuth_group())) {
	        	throw new IllegalStateException("회사 공용 일정은 HR 관리자만 등록할 수 있습니다.");
	        }
	    }
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
	
	public void deleteRoomRsvn(List<Long> rsvnList) {
		schedDAO.deleteRoomRsvn(rsvnList);
	}
	
	public void addMeetingSchedules(RoomRsvnDTO rsvn, String users_id) {
		SchedulesDTO dto = new SchedulesDTO();
		dto.setTitle(rsvn.getTitle());
		dto.setUsers_id(users_id);
		dto.setStart_dt(rsvn.getStart_dt());
		dto.setEnd_dt(rsvn.getEnd_dt());
		dto.setSked_reason(rsvn.getTitle());
		dto.setRef_seq(rsvn.getRsvn_seq());
		
		schedDAO.addMeetingSchedules(dto);
	}
	
	public void deleteMeetingSchedule(Long rsvn_seq, String users_id) {
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
	
	public void deleteProjectMemberSchedule(Long project_seq) {
		schedDAO.deleteProjectMemberSchedule(project_seq);
	}
	
	public void deleteProjectScheduleBySeq(Long project_seq) {
		schedDAO.deleteProjectScheduleBySeq(project_seq);
	}
}
