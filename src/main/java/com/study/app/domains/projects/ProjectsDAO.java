package com.study.app.domains.projects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectsDAO {
	
	@Autowired
	private SqlSessionTemplate batis;
	
	public void insertProject(String loginId, ProjectsDTO dto) {
		Map<String, Object> params = new HashMap<>();
		params.put("loginId", loginId);
		params.put("dto", dto);
		batis.insert("Projects.insertProject", params);
	}
	
	public void insertProjectMembers(ProjectMembersDTO dto) {
		batis.insert("Projects.insertProjectMembers", dto);
	}
	
	public List<ProjectsDTO> getAllProject(String loginId) {
		return batis.selectList("Projects.getAllProject", loginId);
	}
	
	public List<ProjectsDTO> getMyAllProject(String loginId) {
		return batis.selectList("Projects.getMyAllProject", loginId);
	}
	
	public void updateProject(ProjectsDTO dto) {
		batis.update("Projects.updateProject", dto);
	}
	
	public void deleteProjectMembers(Long project_seq) {
		batis.delete("Projects.deleteProjectMembers", project_seq);
	}
	
	public void deleteProject(Long project_seq) {
		batis.delete("Projects.deleteProject", project_seq);
	}
}
