package com.study.app.domains.schedules;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.meetingRooms.RoomRsvnDTO;
import com.study.app.domains.projects.ProjectMembersDTO;
import com.study.app.domains.projects.ProjectsDTO;
import com.study.app.domains.users.UsersDAO;
import com.study.app.domains.users.UsersDTO;
import com.study.app.domains.users.UsersRoleService;

@Service
public class SchedulesService {
	
	@Autowired
	private SchedulesDAO schedDAO;
	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private UsersRoleService usersRoleServ;
	
	public int insertSchedules(SchedulesDTO dto, String usersId) {
		Set<String> companyCategories = Set.of("COMPANY", "TEAM", "ANNIVERSARY", "holiday");
		if (companyCategories.contains(dto.getSchedule_type())) {
	        if (!usersRoleServ.isHrAuthorized(usersId)) {
	            throw new IllegalStateException("회사 공용 일정은 관리자만 등록할 수 있습니다.");
	        }
	    }
		return schedDAO.insertSchedules(dto);
	}
	
	public List<SchedulesDTO> getSchedules(String usersId) {
	    return schedDAO.getSchedules(usersId);
	}
	
	public int deleteSchedules(Long schedule_seq, String usersId) {
	    Set<String> companyCategories = Set.of("COMPANY", "TEAM", "ANNIVERSARY", "holiday");
	    SchedulesDTO target = schedDAO.getScheduleById(schedule_seq);

	    if (target == null) {
	        throw new IllegalStateException("존재하지 않는 일정입니다.");
	    }

	    if (companyCategories.contains(target.getSchedule_type())) {
	        if (!usersRoleServ.isHrAuthorized(usersId)) {
	            throw new IllegalStateException("회사 공용 일정은 관리자만 삭제할 수 있습니다.");
	        }
	    } else {
	        if (!usersId.equals(target.getUsers_id())) {
	            throw new IllegalStateException("본인의 일정만 삭제할 수 있습니다.");
	        }
	    }

	    return schedDAO.deleteSchedules(schedule_seq);
	}
	
	public int updateSchedules(SchedulesDTO dto, String usersId) {
	    Set<String> companyCategories = Set.of("COMPANY", "TEAM", "ANNIVERSARY", "holiday");
	    if (companyCategories.contains(dto.getSchedule_type())) {
	        if (!usersRoleServ.isHrAuthorized(usersId)) {
	            throw new IllegalStateException("회사 공용 일정은 관리자만 수정할 수 있습니다.");
	        }
	    }
	    return schedDAO.updateSchedules(dto);
	}
	
	public List<SchedulesDTO> getApprovedVacations(String usersId) {
	    return schedDAO.getApprovedVacations(usersId);
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
