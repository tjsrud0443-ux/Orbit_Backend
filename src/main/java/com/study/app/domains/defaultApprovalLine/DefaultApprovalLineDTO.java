package com.study.app.domains.defaultApprovalLine;

import java.time.LocalDateTime;

public class DefaultApprovalLineDTO {
	private Long line_seq;
	private String doc_type;
	private Long drafter_rank_seq;
	private Long step_order;
	private String approver_scope;
	private Long approver_rank_seq;
	private Long target_dept_seq;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
	private String rank_name;
	private String dept_name;
	
	public DefaultApprovalLineDTO() {}
	public DefaultApprovalLineDTO(Long line_seq, String doc_type, Long drafter_rank_seq, Long step_order,
			String approver_scope, Long approver_rank_seq, Long target_dept_seq, LocalDateTime created_at,
			LocalDateTime updated_at, String rank_name, String dept_name) {
		super();
		this.line_seq = line_seq;
		this.doc_type = doc_type;
		this.drafter_rank_seq = drafter_rank_seq;
		this.step_order = step_order;
		this.approver_scope = approver_scope;
		this.approver_rank_seq = approver_rank_seq;
		this.target_dept_seq = target_dept_seq;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.rank_name = rank_name;
		this.dept_name = dept_name;
	}
	public Long getLine_seq() {
		return line_seq;
	}
	public void setLine_seq(Long line_seq) {
		this.line_seq = line_seq;
	}
	public String getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}
	public Long getDrafter_rank_seq() {
		return drafter_rank_seq;
	}
	public void setDrafter_rank_seq(Long drafter_rank_seq) {
		this.drafter_rank_seq = drafter_rank_seq;
	}
	public Long getStep_order() {
		return step_order;
	}
	public void setStep_order(Long step_order) {
		this.step_order = step_order;
	}
	public String getApprover_scope() {
		return approver_scope;
	}
	public void setApprover_scope(String approver_scope) {
		this.approver_scope = approver_scope;
	}
	public Long getApprover_rank_seq() {
		return approver_rank_seq;
	}
	public void setApprover_rank_seq(Long approver_rank_seq) {
		this.approver_rank_seq = approver_rank_seq;
	}
	public Long getTarget_dept_seq() {
		return target_dept_seq;
	}
	public void setTarget_dept_seq(Long target_dept_seq) {
		this.target_dept_seq = target_dept_seq;
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
	public String getRank_name() {
		return rank_name;
	}
	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
}
