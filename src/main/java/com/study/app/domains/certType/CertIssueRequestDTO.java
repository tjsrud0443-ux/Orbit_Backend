package com.study.app.domains.certType;

import java.time.LocalDateTime;

public class CertIssueRequestDTO {
	
	private Long cert_request_seq;
	private Long cert_type_seq;
	private String users_id;
	private String request_reason;
	private String status;
	private LocalDateTime requested_at;
	private String handle_id;
	private LocalDateTime approved_at;
	private LocalDateTime rejected_at;
	private String reject_reason;
	private LocalDateTime print_available_at;
	private LocalDateTime print_expires_at;
	private Long applied_print_days;
	private Long applied_max_print;
	private Long printed_count;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	private String issue_date_code;
	private Long issue_no;
	
	private String name;
	private String dept_name;
	private String rank_name;
	private String cert_type_name;
	private String handler_name;
	private LocalDateTime processed_at;
	
	public CertIssueRequestDTO() {}
	
	public CertIssueRequestDTO(Long cert_request_seq, Long cert_type_seq, String users_id, String request_reason,
			String status, LocalDateTime requested_at, String handle_id, LocalDateTime approved_at,
			LocalDateTime rejected_at, String reject_reason, LocalDateTime print_available_at, LocalDateTime print_expires_at, Long applied_print_days,
			Long applied_max_print, Long printed_count, LocalDateTime created_at, LocalDateTime updated_at,
			String issue_date_code, Long issue_no) {
		super();
		this.cert_request_seq = cert_request_seq;
		this.cert_type_seq = cert_type_seq;
		this.users_id = users_id;
		this.request_reason = request_reason;
		this.status = status;
		this.requested_at = requested_at;
		this.handle_id = handle_id;
		this.approved_at = approved_at;
		this.rejected_at = rejected_at;
		this.reject_reason = reject_reason;
		this.print_available_at = print_available_at;
		this.print_expires_at = print_expires_at;
		this.applied_print_days = applied_print_days;
		this.applied_max_print = applied_max_print;
		this.printed_count = printed_count;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.applied_max_print = applied_max_print;
		this.issue_date_code = issue_date_code;
		this.issue_no = issue_no;
	}
	
	public Long getCert_request_seq() {
		return cert_request_seq;
	}
	public void setCert_request_seq(Long cert_request_seq) {
		this.cert_request_seq = cert_request_seq;
	}
	public Long getCert_type_seq() {
		return cert_type_seq;
	}
	public void setCert_type_seq(Long cert_type_seq) {
		this.cert_type_seq = cert_type_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getRequest_reason() {
		return request_reason;
	}
	public void setRequest_reason(String request_reason) {
		this.request_reason = request_reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getRequested_at() {
		return requested_at;
	}
	public void setRequested_at(LocalDateTime requested_at) {
		this.requested_at = requested_at;
	}
	public String getHandle_id() {
		return handle_id;
	}
	public void setHandle_id(String handle_id) {
		this.handle_id = handle_id;
	}
	public LocalDateTime getApproved_at() {
		return approved_at;
	}
	public void setApproved_at(LocalDateTime approved_at) {
		this.approved_at = approved_at;
	}
	public LocalDateTime getRejected_at() {
		return rejected_at;
	}
	public void setRejected_at(LocalDateTime rejected_at) {
		this.rejected_at = rejected_at;
	}
	public String getReject_reason() {
		return reject_reason;
	}
	public void setReject_reason(String reject_reason) {
		this.reject_reason = reject_reason;
	}
	public LocalDateTime getPrint_available_at() {
		return print_available_at;
	}
	public void setPrint_available_at(LocalDateTime print_available_at) {
		this.print_available_at = print_available_at;
	}
	public LocalDateTime getPrint_expires_at() {
		return print_expires_at;
	}
	public void setPrint_expires_at(LocalDateTime print_expires_at) {
		this.print_expires_at = print_expires_at;
	}
	public Long getApplied_print_days() {
		return applied_print_days;
	}
	public void setApplied_print_days(Long applied_print_days) {
		this.applied_print_days = applied_print_days;
	}
	public Long getApplied_max_print() {
		return applied_max_print;
	}
	public void setApplied_max_print(Long applied_max_print) {
		this.applied_max_print = applied_max_print;
	}
	public Long getPrinted_count() {
		return printed_count;
	}
	public void setPrinted_count(Long printed_count) {
		this.printed_count = printed_count;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	public String getIssue_date_code() {
		return issue_date_code;
	}
	public void setIssue_date_code(String issue_date_code) {
		this.issue_date_code = issue_date_code;
	}
	public Long getIssue_no() {
		return issue_no;
	}
	public void setIssue_no(Long issue_no) {
		this.issue_no = issue_no;
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
	public String getCert_type_name() {
		return cert_type_name;
	}
	public void setCert_type_name(String cert_type_name) {
		this.cert_type_name = cert_type_name;
	}
	public String getHandler_name() {
		return handler_name;
	}
	public void setHandler_name(String handler_name) {
		this.handler_name = handler_name;
	}
	public LocalDateTime getProcessed_at() {
		return processed_at;
	}
	public void setProcessed_at(LocalDateTime processed_at) {
		this.processed_at = processed_at;
	}
}
