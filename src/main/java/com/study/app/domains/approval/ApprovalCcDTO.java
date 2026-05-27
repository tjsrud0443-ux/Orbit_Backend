package com.study.app.domains.approval;

public class ApprovalCcDTO {
	
	private Long cc_seq;
	private Long doc_seq;
	private String users_id;
	
	public ApprovalCcDTO() {}
	public ApprovalCcDTO(Long cc_seq, Long doc_seq, String users_id) {
		super();
		this.cc_seq = cc_seq;
		this.doc_seq = doc_seq;
		this.users_id = users_id;
	}
	public Long getCc_seq() {
		return cc_seq;
	}
	public void setCc_seq(Long cc_seq) {
		this.cc_seq = cc_seq;
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
}
