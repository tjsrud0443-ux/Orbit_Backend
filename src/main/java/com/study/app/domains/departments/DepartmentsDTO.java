package com.study.app.domains.departments;

public class DepartmentsDTO {

	private Long dept_seq;
	private String dept_name;
	private String dept_code;
	private Long parent_dept_seq;
	private String created_at;
	private String dept_type; // Admin 부서관리 본부/부서 생성때문에 DTO에만 추가
	private String auth_group;

	public DepartmentsDTO() {}

	public DepartmentsDTO(Long dept_seq, String dept_name, String dept_code, 
			Long parent_dept_seq, String created_at, String dept_type, String auth_group) {
		super();
		this.dept_seq = dept_seq;
		this.dept_name = dept_name;
		this.dept_code = dept_code;
		this.parent_dept_seq = parent_dept_seq;
		this.created_at = created_at;
		this.dept_type = dept_type;
		this.auth_group = auth_group;
	}

	public Long getDept_seq() {
		return dept_seq;
	}

	public void setDept_seq(Long dept_seq) {
		this.dept_seq = dept_seq;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public Long getParent_dept_seq() {
		return parent_dept_seq;
	}

	public void setParent_dept_seq(Long parent_dept_seq) {
		this.parent_dept_seq = parent_dept_seq;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getDept_type() {
		return dept_type;
	}

	public void setDept_type(String dept_type) {
		this.dept_type = dept_type;
	}
	
	public String getAuth_group() {
		return auth_group;
	}

	public void setAuth_group(String auth_group) {
		this.auth_group = auth_group;
	}
}