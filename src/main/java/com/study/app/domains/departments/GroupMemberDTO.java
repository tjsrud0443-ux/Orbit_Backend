package com.study.app.domains.departments;

public class GroupMemberDTO {
	private String id;
	private String name;
	private String position;
	private String sysname;
	
	public GroupMemberDTO() {}
	
	public GroupMemberDTO(String id, String name, String position, String sysname) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.sysname = sysname;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
}