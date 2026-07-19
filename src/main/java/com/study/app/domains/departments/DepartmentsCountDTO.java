package com.study.app.domains.departments;

public class DepartmentsCountDTO {
	private String deptName;
	private Long employeeCount;
	private Long deptSeq;
	
	public DepartmentsCountDTO() {}
	
	public DepartmentsCountDTO(String deptName, Long employeeCount, Long deptSeq) {
		super();
		this.deptName = deptName;
		this.employeeCount = employeeCount;
		this.deptSeq = deptSeq;
	}
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getEmployeeCount() {
		return employeeCount;
	}
	public void setEmployeeCount(Long employeeCount) {
		this.employeeCount = employeeCount;
	}
	public Long getDeptSeq() {
		return deptSeq;
	}
	public void setDeptSeq(Long deptSeq) {
		this.deptSeq = deptSeq;
	}
}