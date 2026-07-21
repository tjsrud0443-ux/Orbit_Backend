package com.study.app.domains.certType;

import java.time.LocalDateTime;

public class CertTypeDTO {
	private Long cert_type_seq;
	private String cert_type_name;
	private String hidden_yn;
	private LocalDateTime created_at;
	private String cert_type_code;
	private String manage_auth_group;
	private Long print_days;
	private Long max_print_count;
	private LocalDateTime updated_at;
	private String cert_description;
	
	private Long cert_request_seq;
	private String request_reason;
	private String status;
	private LocalDateTime requested_at;
	private LocalDateTime approved_at;
	private LocalDateTime rejected_at;
	private String reject_reason;
	private LocalDateTime print_available_at;
	private LocalDateTime print_expires_at;
	private Long applied_print_days;
	private Long applied_max_print;
	private Long printed_count;
	
	public CertTypeDTO() {}
	
	public CertTypeDTO(Long cert_type_seq, String cert_type_name, String hidden_yn, LocalDateTime created_at,
			String cert_type_code, String manage_auth_group, Long print_days, Long max_print_count, LocalDateTime updated_at
			, String cert_description) {
		super();
		this.cert_type_seq = cert_type_seq;
		this.cert_type_name = cert_type_name;
		this.hidden_yn = hidden_yn;
		this.created_at = created_at;
		this.cert_type_code = cert_type_code;
		this.manage_auth_group = manage_auth_group;
		this.print_days = print_days;
		this.max_print_count = max_print_count;
		this.updated_at = updated_at;
		this.cert_description = cert_description;
	}
	
	public Long getCert_type_seq() {
		return cert_type_seq;
	}
	public void setCert_type_seq(Long cert_type_seq) {
		this.cert_type_seq = cert_type_seq;
	}
	public String getCert_type_name() {
		return cert_type_name;
	}
	public void setCert_type_name(String cert_type_name) {
		this.cert_type_name = cert_type_name;
	}
	public String getHidden_yn() {
		return hidden_yn;
	}
	public void setHidden_yn(String hidden_yn) {
		this.hidden_yn = hidden_yn;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public String getCert_type_code() {
		return cert_type_code;
	}
	public void setCert_type_code(String cert_type_code) {
		this.cert_type_code = cert_type_code;
	}
	public String getManage_auth_group() {
		return manage_auth_group;
	}
	public void setManage_auth_group(String manage_auth_group) {
		this.manage_auth_group = manage_auth_group;
	}
	public Long getPrint_days() {
		return print_days;
	}
	public void setPrint_days(Long print_days) {
		this.print_days = print_days;
	}
	public Long getMax_print_count() {
		return max_print_count;
	}
	public void setMax_print_count(Long max_print_count) {
		this.max_print_count = max_print_count;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	public String getCert_description() {
		return cert_description;
	}
	public void setCert_description(String cert_description) {
		this.cert_description = cert_description;
	}
	public Long getCert_request_seq() {
		return cert_request_seq;
	}
	public void setCert_request_seq(Long cert_request_seq) {
		this.cert_request_seq = cert_request_seq;
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
}
