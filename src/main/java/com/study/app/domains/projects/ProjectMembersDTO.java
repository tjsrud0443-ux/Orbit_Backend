package com.study.app.domains.projects;

public class ProjectMembersDTO {
	
	private Long member_seq;
	private Long project_seq;
	private String users_id;
	private String join_at;
	private String name;
	private String sysname;
	
	public ProjectMembersDTO() {}
	
	public ProjectMembersDTO(Long member_seq, Long project_seq, String users_id, String join_at) {
		super();
		this.member_seq = member_seq;
		this.project_seq = project_seq;
		this.users_id = users_id;
		this.join_at = join_at;
	}
	
	public Long getMember_seq() {
		return member_seq;
	}
	public void setMember_seq(Long member_seq) {
		this.member_seq = member_seq;
	}
	public Long getProject_seq() {
		return project_seq;
	}
	public void setProject_seq(Long project_seq) {
		this.project_seq = project_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getJoin_at() {
		return join_at;
	}
	public void setJoin_at(String join_at) {
		this.join_at = join_at;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}	
}
