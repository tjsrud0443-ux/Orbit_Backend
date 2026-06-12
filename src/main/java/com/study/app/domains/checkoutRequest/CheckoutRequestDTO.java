package com.study.app.domains.checkoutRequest;

public class CheckoutRequestDTO {
	private Long checkout_seq;
	private Long attendance_seq;
	private String users_id;
	private String checkout_date;
	private String req_check_out;
	private String reason;
	private String status;
	private String approver_id;
	private String approved_at;
	
	private String name;
	private String approver_name;
	private String dept_name;
	private String rank_name;
	
	public CheckoutRequestDTO() {}
	public CheckoutRequestDTO(Long checkout_seq, Long attendance_seq, String users_id, String checkout_date,
			String req_check_out, String reason, String status, String approver_id, String approved_at, String name,
			String approver_name, String dept_name, String rank_name) {
		super();
		this.checkout_seq = checkout_seq;
		this.attendance_seq = attendance_seq;
		this.users_id = users_id;
		this.checkout_date = checkout_date;
		this.req_check_out = req_check_out;
		this.reason = reason;
		this.status = status;
		this.approver_id = approver_id;
		this.approved_at = approved_at;
		this.name = name;
		this.approver_name = approver_name;
		this.dept_name = dept_name;
		this.rank_name = rank_name;
	}
	public Long getCheckout_seq() {
		return checkout_seq;
	}
	public void setCheckout_seq(Long checkout_seq) {
		this.checkout_seq = checkout_seq;
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
	public String getCheckout_date() {
		return checkout_date;
	}
	public void setCheckout_date(String checkout_date) {
		this.checkout_date = checkout_date;
	}
	public String getReq_check_out() {
		return req_check_out;
	}
	public void setReq_check_out(String req_check_out) {
		this.req_check_out = req_check_out;
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
