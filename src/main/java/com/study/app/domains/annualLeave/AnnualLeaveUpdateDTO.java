package com.study.app.domains.annualLeave;

public class AnnualLeaveUpdateDTO {
	
	private String users_id;
	private String hire_date;
	private Long leave_seq;
	private double total_days;
	private double used_days;
	
	public AnnualLeaveUpdateDTO() {}
	
	public AnnualLeaveUpdateDTO(String users_id, String hire_date, Long leave_seq,double total_days, double used_days) {
		super();
		this.users_id = users_id;
		this.hire_date = hire_date;
		this.leave_seq = leave_seq;
		this.total_days = total_days;
		this.used_days = used_days;
	}
	
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
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
	public double getTotal_days() {
		return total_days;
	}
	public void setTotal_days(double total_days) {
		this.total_days = total_days;
	}
	public double getUsed_days() {
		return used_days;
	}
	public void setUsed_days(double used_days) {
		this.used_days = used_days;
	}
}
