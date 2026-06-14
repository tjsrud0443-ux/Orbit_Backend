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
			noti.setRef_type("PROJECT");
			notiServ.insertNoti(noti);
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
		List<String> beforeMembers = projectsDao.selectProjectMemberIds(projectSeq);
		
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
			noti.setRef_type("PROJECT");
			
			if(beforeMembers.contains(member.getUsers_id())) {
		        noti.setContent("프로젝트 정보가 수정되었습니다.");
		    } else {
		        noti.setContent("프로젝트 일정이 추가되었습니다.");
		    }
			
			notiServ.insertNoti(noti);
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
		kanbanDao.deleteAllTask(project_seq);
		projectsDao.deleteProjectMembers(project_seq);
		projectsDao.deleteProject(project_seq);
	}

	public List<KanbanTaskDTO> getKanbanTaskList(Long project_seq) {
		return kanbanDao.getKanbanTaskList(project_seq);
	}

	public List<ProjectMembersDTO> getProjectMembers(Long project_seq) {
		return kanbanDao.getProjectMembers(project_seq);
	}

	@Transactional
	public void insertTask(String loginId, KanbanTaskDTO dto) {
		Long position = kanbanDao.getNextPosition(dto);

		dto.setPosition(position);
		dto.setUsers_c_id(loginId);
		kanbanDao.insertTask(dto);
		
		Long taskSeq = dto.getTask_seq();
		if(!dto.getUsers_pic_id().equals(loginId)) {
			NotificationsDTO noti = new NotificationsDTO();

			noti.setUsers_id(dto.getUsers_pic_id());
			noti.setRef_seq(taskSeq);
			noti.setNoti_type("TASK");
			noti.setContent("새로운 업무가 배정되었습니다.");
			noti.setRef_type("TASK");

			notiServ.insertNoti(noti);
		}
	}

	public ProjectsDTO getProjectBySeq(Long project_seq) {
		return kanbanDao.getProjectBySeq(project_seq);
	}

	@Transactional
	public void updateTask(String loginId, KanbanTaskDTO dto) {
		String beforeUsersPicId = kanbanDao.selectUsersPicIdBySeq(dto.getTask_seq());

		kanbanDao.updateTask(dto);

		if(!beforeUsersPicId.equals(dto.getUsers_pic_id()) 
				&& !dto.getUsers_pic_id().equals(loginId)) {
			NotificationsDTO noti = new NotificationsDTO();

			noti.setUsers_id(dto.getUsers_pic_id());
			noti.setRef_seq(dto.getTask_seq());
			noti.setNoti_type("TASK");
			noti.setContent("새로운 업무가 배정되었습니다.");
			noti.setRef_type("TASK");

			notiServ.insertNoti(noti);
		}
	}

	public void deleteTask(Long task_seq) {
		kanbanDao.deleteTask(task_seq);
	}

	public void updateTaskStatus(KanbanTaskDTO dto) {
		kanbanDao.updateTaskStatus(dto);
	}
}
