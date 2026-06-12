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
}
