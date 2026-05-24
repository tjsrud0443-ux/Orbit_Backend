package com.study.app.domains.departments;

import java.util.List;

// 트리형 조직도 DTO
public class GroupTreeDTO {
	
	private Long deptSeq;
    private String deptCode;
    private String deptName;
    private String sysname;
    private Long parentDeptSeq;
    private String name;
    private String position;
    private List<GroupTreeDTO> children;
    
	public GroupTreeDTO() {}
	
	public GroupTreeDTO(Long deptSeq, String deptCode, String deptName, String sysname, Long parentDeptSeq, String name,
			String position, List<GroupTreeDTO> children) {
		super();
		this.deptSeq = deptSeq;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.sysname = sysname;
		this.parentDeptSeq = parentDeptSeq;
		this.name = name;
		this.position = position;
		this.children = children;
	}
	
	public Long getDeptSeq() {
		return deptSeq;
	}
	public void setDeptSeq(Long deptSeq) {
		this.deptSeq = deptSeq;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	public Long getParentDeptSeq() {
		return parentDeptSeq;
	}
	public void setParentDeptSeq(Long parentDeptSeq) {
		this.parentDeptSeq = parentDeptSeq;
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
	public List<GroupTreeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<GroupTreeDTO> children) {
		this.children = children;
	}
}