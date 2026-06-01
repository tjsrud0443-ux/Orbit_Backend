package com.study.app.domains.departments;

public class DepartmentsCountDTO {
	private String deptName;
	private Long employeeCount;
	
	public DepartmentsCountDTO() {}
	
	public DepartmentsCountDTO(String deptName, Long employeeCount) {
		super();
		this.deptName = deptName;
		this.employeeCount = employeeCount;
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
}