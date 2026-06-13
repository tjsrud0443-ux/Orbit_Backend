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
	private NotificationsService notiServ;

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
			noti.setNoti_type("PROJECT");
			noti.setContent("프로젝트 일정이 추가되었습니다.");
			notiServ.insertProjectNoti(noti);
		}
		ProjectMembersDTO createUser = new ProjectMembersDTO();
		createUser.setProject_seq(projectSeq);
		createUser.setUsers_id(loginId);
		projectsDao.insertProjectMembers(createUser);
		
		schedServ.insertMyProjectSchedule(loginId, dto);
	}
	
	public List<ProjectsDTO> getProject(String loginId) {
		Long rankSeq = usersDao.selectRankSeqByLoginId(loginId);
		if(rankSeq == 1) {
			return projectsDao.getAllProject(loginId);
		}
		
		return projectsDao.getMyAllProject(loginId);
	}

	@Transactional
	public void updateProject(String loginId, ProjectsDTO dto) {
		projectsDao.updateProject(dto);
		
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
			noti.setNoti_type("PROJECT");
			noti.setContent("프로젝트 정보가 수정되었습니다.");
			notiServ.insertProjectNoti(noti);
		}
		ProjectMembersDTO createUser = new ProjectMembersDTO();
		createUser.setProject_seq(projectSeq);
		createUser.setUsers_id(loginId);
		projectsDao.insertProjectMembers(createUser);
		
		schedServ.insertMyProjectSchedule(loginId, dto);
	}
	
	public void completeProject(Long project_seq) {
		projectsDao.completeProject(project_seq);
	}
	
	@Transactional
	public void deleteProject(Long project_seq) {
		notiServ.deleteProjectNotiBySeq(project_seq);
		schedServ.deleteProjectScheduleBySeq(project_seq);
		projectsDao.deleteProjectMembers(project_seq);
		projectsDao.deleteProject(project_seq);
	}

	public List<KanbanTaskDTO> getKanbanTaskList(Long project_seq) {
		return kanbanDao.getKanbanTaskList(project_seq);
	}

	public List<ProjectMembersDTO> getProjectMembers(Long project_seq) {
		return kanbanDao.getProjectMembers(project_seq);
	}








}
