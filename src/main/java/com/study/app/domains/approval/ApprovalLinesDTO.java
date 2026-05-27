package com.study.app.domains.approval;

public class ApprovalLinesDTO {
	private Long line_seq;
	private Long doc_seq;
	private String users_id;
	private Long step_order;
	private String status;
	private String handle_at;
	private String reject_reason;
	
	public ApprovalLinesDTO() {}
	public ApprovalLinesDTO(Long line_seq, Long doc_seq, String users_id, Long step_order, String status,
			String handle_at, String reject_reason) {
		super();
		this.line_seq = line_seq;
		this.doc_seq = doc_seq;
		this.users_id = users_id;
		this.step_order = step_order;
		this.status = status;
		this.handle_at = handle_at;
		this.reject_reason = reject_reason;
	}
	public Long getLine_seq() {
		return line_seq;
	}
	public void setLine_seq(Long line_seq) {
		this.line_seq = line_seq;
	}
	public Long getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(Long doc_seq) {
		this.doc_seq = doc_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public Long getStep_order() {
		return step_order;
	}
	public void setStep_order(Long step_order) {
		this.step_order = step_order;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHandle_at() {
		return handle_at;
	}
	public void setHandle_at(String handle_at) {
		this.handle_at = handle_at;
	}
	public String getReject_reason() {
		return reject_reason;
	}
	public void setReject_reason(String reject_reason) {
		this.reject_reason = reject_reason;
	}
}
