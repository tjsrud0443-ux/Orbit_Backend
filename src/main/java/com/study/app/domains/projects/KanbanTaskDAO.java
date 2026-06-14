package com.study.app.domains.projects;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class KanbanTaskDAO {
	
	@Autowired
	private SqlSessionTemplate batis;
	
	public List<KanbanTaskDTO> getKanbanTaskList(Long project_seq) {
		return batis.selectList("KanbanTask.getKanbanTaskList", project_seq);
	}
	
	public List<ProjectMembersDTO> getProjectMembers(Long project_seq) {
		return batis.selectList("KanbanTask.getProjectMembers", project_seq);
	}
	
	public Long getNextPosition(KanbanTaskDTO dto) {
		return batis.selectOne("KanbanTask.getNextPosition", dto);
	}
	
	public void insertTask(KanbanTaskDTO dto) {
		batis.insert("KanbanTask.insertTask", dto);
	}
	
	public ProjectsDTO getProjectBySeq(Long project_seq) {
		return batis.selectOne("KanbanTask.getProjectBySeq", project_seq);
	}
	
	public void updateTask(KanbanTaskDTO dto) {
		batis.update("KanbanTask.updateTask", dto);
	}
	
	public void deleteTask(Long task_seq) {
		batis.delete("KanbanTask.deleteTask", task_seq);
	}
	
	public void updateTaskStatus(KanbanTaskDTO dto) {
		batis.update("KanbanTask.updateTaskStatus", dto);
	}
	
	public void deleteAllTask(Long project_seq) {
		batis.delete("KanbanTask.deleteAllTask", project_seq);
	}
	
	public String selectUsersPicIdBySeq(Long task_seq) {
		return batis.selectOne("KanbanTask.selectUsersPicIdBySeq", task_seq);
	}
	
	public Long getNotiProjectSeq(Long task_seq) {
		return batis.selectOne("KanbanTask.getNotiProjectSeq", task_seq);
	}
}
