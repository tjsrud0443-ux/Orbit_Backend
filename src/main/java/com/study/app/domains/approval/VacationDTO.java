package com.study.app.domains.approval;

import java.util.List;

public class VacationDTO extends DraftDocumentsDTO {
	private Long vac_seq;
	private Long doc_seq;
	private String vac_type;
	private String start_date;
	private String end_date;
	private Double days;
	private String reason;
	private Double remaining_days;
	
	private List<ApprovalLinesDTO> approvers;
    private List<ApprovalCcDTO> referrers;
    private List<VacationAttachmentsDTO> attachments;
	
	public VacationDTO() {}
	public VacationDTO(Long vac_seq, Long doc_seq, String vac_type, String start_date, String end_date, Double days,
			String reason, Double remaining_days, List<ApprovalLinesDTO> approvers, List<ApprovalCcDTO> referrers,
			List<VacationAttachmentsDTO> attachments) {
		super();
		this.vac_seq = vac_seq;
		this.doc_seq = doc_seq;
		this.vac_type = vac_type;
		this.start_date = start_date;
		this.end_date = end_date;
		this.days = days;
		this.reason = reason;
		this.remaining_days = remaining_days;
		this.approvers = approvers;
		this.referrers = referrers;
		this.attachments = attachments;
	}
	public Long getVac_seq() {
		return vac_seq;
	}
	public void setVac_seq(Long vac_seq) {
		this.vac_seq = vac_seq;
	}
	public Long getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(Long doc_seq) {
		this.doc_seq = doc_seq;
	}
	public String getVac_type() {
		return vac_type;
	}
	public void setVac_type(String vac_type) {
		this.vac_type = vac_type;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public Double getDays() {
		return days;
	}
	public void setDays(Double days) {
		this.days = days;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Double getRemaining_days() {
		return remaining_days;
	}
	public void setRemaining_days(Double remaining_days) {
		this.remaining_days = remaining_days;
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
	public List<VacationAttachmentsDTO> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<VacationAttachmentsDTO> attachments) {
		this.attachments = attachments;
	}
}
