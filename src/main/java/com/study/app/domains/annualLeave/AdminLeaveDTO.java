package com.study.app.domains.annualLeave;

public class AdminLeaveDTO {
	private Long users_seq;
    private String users_id;
    private String name;
    private String dept_name;
    private String rank_name;
    private String hire_date;
    private Long leave_seq;
    private Double total_days;
    private Double used_days;
    private Double remaining_days;
    private Double admin_days;
    
    private Double delta_days;
    
    public AdminLeaveDTO() {}

	public AdminLeaveDTO(Long users_seq, String users_id, String name, String dept_name, String rank_name,
			String hire_date, Long leave_seq, Double total_days, Double used_days, Double remaining_days,
			Double admin_days, Double delta_days) {
		super();
		this.users_seq = users_seq;
		this.users_id = users_id;
		this.name = name;
		this.dept_name = dept_name;
		this.rank_name = rank_name;
		this.hire_date = hire_date;
		this.leave_seq = leave_seq;
		this.total_days = total_days;
		this.used_days = used_days;
		this.remaining_days = remaining_days;
		this.admin_days = admin_days;
		this.delta_days = delta_days;
	}

	public Long getUsers_seq() {
		return users_seq;
	}

	public void setUsers_seq(Long users_seq) {
		this.users_seq = users_seq;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getRank_name() {
		return rank_name;
	}

	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}

	public String getHire_date() {
		return hire_date;
	}

	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}

	public Long getLeave_seq() {
		return leave_seq;
	}

	public void setLeave_seq(Long leave_seq) {
		this.leave_seq = leave_seq;
	}

	public Double getTotal_days() {
		return total_days;
	}

	public void setTotal_days(Double total_days) {
		this.total_days = total_days;
	}

	public Double getUsed_days() {
		return used_days;
	}

	public void setUsed_days(Double used_days) {
		this.used_days = used_days;
	}

	public Double getRemaining_days() {
		return remaining_days;
	}

	public void setRemaining_days(Double remaining_days) {
		this.remaining_days = remaining_days;
	}

	public Double getAdmin_days() {
		return admin_days;
	}

	public void setAdmin_days(Double admin_days) {
		this.admin_days = admin_days;
	}

	public Double getDelta_days() {
		return delta_days;
	}

	public void setDelta_days(Double delta_days) {
		this.delta_days = delta_days;
	}

}
