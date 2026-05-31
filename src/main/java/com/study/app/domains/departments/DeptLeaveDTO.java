package com.study.app.domains.departments;

public class DeptLeaveDTO {

	private String deptName;
	private Double leave;
	
	public DeptLeaveDTO() {}
	
	public DeptLeaveDTO(String deptName, Double leave) {
		super();
		this.deptName = deptName;
		this.leave = leave;
	}
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Double getLeave() {
		return leave;
	}
	public void setLeave(Double leave) {
		this.leave = leave;
	}
}