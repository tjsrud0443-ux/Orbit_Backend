package com.study.app.domains.overtimeRequest;

public class OvertimeRequestDTO {
	private Long overtime_seq;
	private Long attendance_seq;
	private String users_id;
	private String work_date;
	private String start_dt;
	private String end_dt;
	private Long request_min;
	private String reason;
	private String status;
	private String approver_id;
	private String approved_at;
	
	private String name;
	private String approver_name;
	private String dept_name;
	private String rank_name;
	
	public OvertimeRequestDTO() {}
	public OvertimeRequestDTO(Long overtime_seq, Long attendance_seq, String users_id, String work_date,
			String start_dt, String end_dt, Long request_min, String reason, String status, String approver_id,
			String approved_at, String name, String approver_name, String dept_name, String rank_name) {
		super();
		this.overtime_seq = overtime_seq;
		this.attendance_seq = attendance_seq;
		this.users_id = users_id;
		this.work_date = work_date;
		this.start_dt = start_dt;
		this.end_dt = end_dt;
		this.request_min = request_min;
		this.reason = reason;
		this.status = status;
		this.approver_id = approver_id;
		this.approved_at = approved_at;
		this.name = name;
		this.approver_name = approver_name;
		this.dept_name = dept_name;
		this.rank_name = rank_name;
	}
	public Long getOvertime_seq() {
		return overtime_seq;
	}
	public void setOvertime_seq(Long overtime_seq) {
		this.overtime_seq = overtime_seq;
	}
	public Long getAttendance_seq() {
		return attendance_seq;
	}
	public void setAttendance_seq(Long attendance_seq) {
		this.attendance_seq = attendance_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getWork_date() {
		return work_date;
	}
	public void setWork_date(String work_date) {
		this.work_date = work_date;
	}
	public String getStart_dt() {
		return start_dt;
	}
	public void setStart_dt(String start_dt) {
		this.start_dt = start_dt;
	}
	public String getEnd_dt() {
		return end_dt;
	}
	public void setEnd_dt(String end_dt) {
		this.end_dt = end_dt;
	}
	public Long getRequest_min() {
		return request_min;
	}
	public void setRequest_min(Long request_min) {
		this.request_min = request_min;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprover_id() {
		return approver_id;
	}
	public void setApprover_id(String approver_id) {
		this.approver_id = approver_id;
	}
	public String getApproved_at() {
		return approved_at;
	}
	public void setApproved_at(String approved_at) {
		this.approved_at = approved_at;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApprover_name() {
		return approver_name;
	}
	public void setApprover_name(String approver_name) {
		this.approver_name = approver_name;
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
}
