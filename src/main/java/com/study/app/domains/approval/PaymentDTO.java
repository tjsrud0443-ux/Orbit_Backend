package com.study.app.domains.approval;

import java.util.List;

public class PaymentDTO extends DraftDocumentsDTO {
	
	private Long pay_seq;
	private Long doc_seq;
	private String pay_date;
	private Double total_amount;
	private String pay_reason;
	private String account_info;
	
	private List<ApprovalLinesDTO> approvers;
    private List<ApprovalCcDTO> referrers;
    
    public PaymentDTO() {}
	public PaymentDTO(Long pay_seq, Long doc_seq, String pay_date, Double total_amount, String pay_reason,
			String account_info, List<ApprovalLinesDTO> approvers, List<ApprovalCcDTO> referrers) {
		super();
		this.pay_seq = pay_seq;
		this.doc_seq = doc_seq;
		this.pay_date = pay_date;
		this.total_amount = total_amount;
		this.pay_reason = pay_reason;
		this.account_info = account_info;
		this.approvers = approvers;
		this.referrers = referrers;
	}
	public Long getPay_seq() {
		return pay_seq;
	}
	public void setPay_seq(Long pay_seq) {
		this.pay_seq = pay_seq;
	}
	public Long getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(Long doc_seq) {
		this.doc_seq = doc_seq;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	public Double getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}
	public String getPay_reason() {
		return pay_reason;
	}
	public void setPay_reason(String pay_reason) {
		this.pay_reason = pay_reason;
	}
	public String getAccount_info() {
		return account_info;
	}
	public void setAccount_info(String account_info) {
		this.account_info = account_info;
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
