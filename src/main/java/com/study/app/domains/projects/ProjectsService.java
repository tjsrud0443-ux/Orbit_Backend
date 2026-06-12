package com.study.app.domains.projects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.app.domains.notifications.NotificationsDTO;
import com.study.app.domains.notifications.NotificationsService;
import com.study.app.domains.schedules.SchedulesService;
import com.study.app.domains.users.UsersDAO;
import com.study.app.domains.users.UsersDTO;

@Service
public class ProjectsService {

	@Autowired
	private ProjectsDAO projectsDao;

	@Autowired
	private KanbanTaskDAO kanbanDao;

	@Autowired
	private SchedulesService schedServ;
	
	@Autowired
	private UsersDAO usersDao;
	
	@Autowired
	private NotificationsService noriServ;

	public List<UsersDTO> allEmployee() {
		return usersDao.getAllEmployees();
	}

	@Transactional
	public void insertProjectAndMembers(String loginId, ProjectsDTO dto) {
		projectsDao.insertProject(loginId, dto);

		Long projectSeq = dto.getProject_seq();

		for(ProjectMembersDTO member : dto.getProjectMembersDTO()) {
			member.setProject_seq(projectSeq);
			projectsDao.insertProjectMembers(member);
			
			schedServ.insertProjectMemberSchedule(projectSeq, dto, member); 
			
			NotificationsDTO noti = new NotificationsDTO();
			noti.setRef_seq(projectSeq);
			noti.setUsers_id(member.getUsers_id());
			noti.setContent("프로젝트 일정이 추가되었습니다.");
			noriServ.insertProjectNoti(noti);
		}
		schedServ.insertMyProjectSchedule(loginId, dto);
	}
	
	public List<ProjectsDTO> getAllProject(String loginId) {
		return projectsDao.getAllProject(loginId);
	}

	@Transactional
	public void projectUpdate(String loginId, ProjectsDTO dto) {
		projectsDao.projectUpdate(dto);
		
		Long projectSeq = dto.getProject_seq();
		
		projectsDao.deleteProjectMembers(projectSeq);
		schedServ.deleteProjectMemberSchedule(projectSeq); 
		
		for(ProjectMembersDTO member : dto.getProjectMembersDTO()) {
			member.setProject_seq(projectSeq);
			projectsDao.insertProjectMembers(member);
			
			schedServ.insertProjectMemberSchedule(projectSeq, dto, member); 
			
			NotificationsDTO noti = new NotificationsDTO();
			noti.setRef_seq(projectSeq);
			noti.setUsers_id(member.getUsers_id());
			noti.setContent("프로젝트 정보가 수정되었습니다.");
			noriServ.insertProjectNoti(noti);
		}
		schedServ.insertMyProjectSchedule(loginId, dto);
	}












}
