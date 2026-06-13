package com.study.app.domains.projects;

import java.util.List;

public class ProjectsDTO {

	private Long project_seq;
	private String project_name;
	private String contents;
	private String start_date;
	private String end_date;
	private String status;
	private String users_id;
	private String created_at;
	private List<ProjectMembersDTO> projectMembersDTO;
	private String created_name;
	
	public ProjectsDTO() {}
	
	public ProjectsDTO(Long project_seq, String project_name, String contents, String start_date, String end_date,
			String status, String users_id, String created_at) {
		super();
		this.project_seq = project_seq;
		this.project_name = project_name;
		this.contents = contents;
		this.start_date = start_date;
		this.end_date = end_date;
		this.status = status;
		this.users_id = users_id;
		this.created_at = created_at;
	}
	
	public Long getProject_seq() {
		return project_seq;
	}
	public void setProject_seq(Long project_seq) {
		this.project_seq = project_seq;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public List<ProjectMembersDTO> getProjectMembersDTO() {
		return projectMembersDTO;
	}
	public void setProjectMembersDTO(List<ProjectMembersDTO> projectMembersDTO) {
		this.projectMembersDTO = projectMembersDTO;
	}
	public String getCreated_name() {
		return created_name;
	}
	public void setCreated_name(String created_name) {
		this.created_name = created_name;
	}
}
