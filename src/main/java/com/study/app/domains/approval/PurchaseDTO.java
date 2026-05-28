package com.study.app.domains.approval;

import java.util.List;

public class PurchaseDTO extends DraftDocumentsDTO {
	private Long purchase_seq;
	private Long doc_seq;
	private Double total_amount;
	private String purpose;
	private String vendor;
	private String purchase_date;
	
	private List<ApprovalLinesDTO> approvers;
    private List<ApprovalCcDTO> referrers;
    
    public PurchaseDTO() {}
	public PurchaseDTO(Long purchase_seq, Long doc_seq, Double total_amount, String purpose, String vendor,
			String purchase_date, List<ApprovalLinesDTO> approvers, List<ApprovalCcDTO> referrers) {
		super();
		this.purchase_seq = purchase_seq;
		this.doc_seq = doc_seq;
		this.total_amount = total_amount;
		this.purpose = purpose;
		this.vendor = vendor;
		this.purchase_date = purchase_date;
		this.approvers = approvers;
		this.referrers = referrers;
	}
	public Long getPurchase_seq() {
		return purchase_seq;
	}
	public void setPurchase_seq(Long purchase_seq) {
		this.purchase_seq = purchase_seq;
	}
	public Long getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(Long doc_seq) {
		this.doc_seq = doc_seq;
	}
	public Double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getPurchase_date() {
		return purchase_date;
	}
	public void setPurchase_date(String purchase_date) {
		this.purchase_date = purchase_date;
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
