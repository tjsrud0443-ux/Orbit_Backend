package com.study.app.domains.approval;

import java.util.List;

public class GeneralDTO extends DraftDocumentsDTO{
	private Long general_seq;
	private Long doc_seq;
	private String purpose;
	private String content;
	
	private List<ApprovalLinesDTO> approvers;
    private List<ApprovalCcDTO> referrers;
	
	public GeneralDTO() {}
	public GeneralDTO(Long general_seq, Long doc_seq, String purpose, String content, List<ApprovalLinesDTO> approvers,
			List<ApprovalCcDTO> referrers) {
		super();
		this.general_seq = general_seq;
		this.doc_seq = doc_seq;
		this.purpose = purpose;
		this.content = content;
		this.approvers = approvers;
		this.referrers = referrers;
	}
	public Long getGeneral_seq() {
		return general_seq;
	}
	public void setGeneral_seq(Long general_seq) {
		this.general_seq = general_seq;
	}
	public Long getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(Long doc_seq) {
		this.doc_seq = doc_seq;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
}
