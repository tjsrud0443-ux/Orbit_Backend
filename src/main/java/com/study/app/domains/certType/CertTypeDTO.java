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
}
