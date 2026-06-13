package com.study.app.domains.projects;

public class KanbanTaskDTO {
	
	private Long task_seq;
	private Long project_seq;
	private String title;
	private String content;
	private String status;
	private String priority;
	private String users_pic_id;
	private String start_date;
	private String due_date;
	private Long position;
	private String users_c_id;
	private String created_at;
	private String updated_at;
	private String name;
	private String sysname;
	
	public KanbanTaskDTO() {}
	
	public KanbanTaskDTO(Long task_seq, Long project_seq, String title, String content, String status, String priority,
			String users_pic_id, String start_date, String due_date, Long position, String users_c_id,
			String created_at, String updated_at) {
		super();
		this.task_seq = task_seq;
		this.project_seq = project_seq;
		this.title = title;
		this.content = content;
		this.status = status;
		this.priority = priority;
		this.users_pic_id = users_pic_id;
		this.start_date = start_date;
		this.due_date = due_date;
		this.position = position;
		this.users_c_id = users_c_id;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public Long getTask_seq() {
		return task_seq;
	}
	public void setTask_seq(Long task_seq) {
		this.task_seq = task_seq;
	}
	public Long getProject_seq() {
		return project_seq;
	}
	public void setProject_seq(Long project_seq) {
		this.project_seq = project_seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getUsers_pic_id() {
		return users_pic_id;
	}
	public void setUsers_pic_id(String users_pic_id) {
		this.users_pic_id = users_pic_id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public Long getPosition() {
		return position;
	}
	public void setPosition(Long position) {
		this.position = position;
	}
	public String getUsers_c_id() {
		return users_c_id;
	}
	public void setUsers_c_id(String users_c_id) {
		this.users_c_id = users_c_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
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
