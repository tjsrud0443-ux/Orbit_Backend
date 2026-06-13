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
}
