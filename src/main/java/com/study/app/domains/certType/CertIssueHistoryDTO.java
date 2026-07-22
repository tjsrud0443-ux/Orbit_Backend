package com.study.app.domains.certType;

import java.time.LocalDateTime;

public class CertIssueHistoryDTO {
	
	private Long issue_seq;
	private Long cert_request_seq;
	private String issue_date_code;
	private Long issue_no;
	private LocalDateTime printed_at;
	
	private String users_id;
	private String name;
	private String dept_name;
	private String rank_name;
	private String cert_type_name;
	private String request_reason;
	private LocalDateTime approved_at;
	
	private String issue_number;
	
	public CertIssueHistoryDTO() {}
	
	public CertIssueHistoryDTO(Long issue_seq, Long cert_request_seq, String issue_date_code, 
			Long issue_no, LocalDateTime printed_at) {
		super();
		this.issue_seq = issue_seq;
		this.cert_request_seq = cert_request_seq;
		this.issue_date_code = issue_date_code;
		this.issue_no = issue_no;
		this.printed_at = printed_at;
	}
	
	public Long getIssue_seq() {
		return issue_seq;
	}
	public void setIssue_seq(Long issue_seq) {
		this.issue_seq = issue_seq;
	}
	public Long getCert_request_seq() {
		return cert_request_seq;
	}
	public void setCert_request_seq(Long cert_request_seq) {
		this.cert_request_seq = cert_request_seq;
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
	public LocalDateTime getPrinted_at() {
		return printed_at;
	}
	public void setPrinted_at(LocalDateTime printed_at) {
		this.printed_at = printed_at;
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
	public String getCert_type_name() {
		return cert_type_name;
	}
	public void setCert_type_name(String cert_type_name) {
		this.cert_type_name = cert_type_name;
	}
	public String getRequest_reason() {
		return request_reason;
	}
	public void setRequest_reason(String request_reason) {
		this.request_reason = request_reason;
	}
	public LocalDateTime getApproved_at() {
		return approved_at;
	}
	public void setApproved_at(LocalDateTime approved_at) {
		this.approved_at = approved_at;
	}
	public String getIssue_number() {
		return issue_number;
	}
	public void setIssue_number(String issue_number) {
		this.issue_number = issue_number;
	}
}
