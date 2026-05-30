package com.study.app.domains.approval;

import java.util.List;

public class DraftDocumentsDTO {
	
	private Long doc_seq;
	private String title;
	private String doc_type;
	private String users_id;
	private String draft_date;
	private String status;
	private String reject_reason;
	private Long is_temp;
	private String temp_expires_at;
	private String created_at;
	private String updated_at;
	
	private List<ApprovalLinesDTO> approvers;
    private List<ApprovalCcDTO> referrers;
    
    public DraftDocumentsDTO() {}
	public DraftDocumentsDTO(Long doc_seq, String title, String doc_type, String users_id, String draft_date,
			String status, String reject_reason, Long is_temp, String temp_expires_at, String created_at,
			String updated_at, List<ApprovalLinesDTO> approvers, List<ApprovalCcDTO> referrers) {
		super();
		this.doc_seq = doc_seq;
		this.title = title;
		this.doc_type = doc_type;
		this.users_id = users_id;
		this.draft_date = draft_date;
		this.status = status;
		this.reject_reason = reject_reason;
		this.is_temp = is_temp;
		this.temp_expires_at = temp_expires_at;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.approvers = approvers;
		this.referrers = referrers;
	}
	public Long getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(Long doc_seq) {
		this.doc_seq = doc_seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getDraft_date() {
		return draft_date;
	}
	public void setDraft_date(String draft_date) {
		this.draft_date = draft_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReject_reason() {
		return reject_reason;
	}
	public void setReject_reason(String reject_reason) {
		this.reject_reason = reject_reason;
	}
	public Long getIs_temp() {
		return is_temp;
	}
	public void setIs_temp(Long is_temp) {
		this.is_temp = is_temp;
	}
	public String getTemp_expires_at() {
		return temp_expires_at;
	}
	public void setTemp_expires_at(String temp_expires_at) {
		this.temp_expires_at = temp_expires_at;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public List<ApprovalLinesDTO> getApprovers() {
		return approvers;
	}
	public void setApprovers(List<ApprovalLinesDTO> approvers) {
		this.approvers = approvers;
	}
	public List<ApprovalCcDTO> getReferrers() {
		return referrers;
	}
	public void setReferrers(List<ApprovalCcDTO> referrers) {
		this.referrers = referrers;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private String name;

	public DraftDocumentsDTO(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
