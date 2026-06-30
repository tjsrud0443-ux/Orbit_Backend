package com.study.app.domains.approval;

import java.util.List;

public class PaymentDTO extends DraftDocumentsDTO {
	
	private Long pay_seq;
	private Long doc_seq;
	private String pay_date;
	private Double total_amount;
	private String pay_reason;
	private String bank_name;
	private String account_holder;
	private String account_number;
	
	private List<ApprovalLinesDTO> approvers;
    private List<ApprovalCcDTO> referrers;
    private List<PaymentItemsDTO> items;
    
    public PaymentDTO() {}
	public PaymentDTO(Long pay_seq, Long doc_seq, String pay_date, Double total_amount, String pay_reason,
			String bank_name, String account_holder, String account_number, List<ApprovalLinesDTO> approvers,
			List<ApprovalCcDTO> referrers, List<PaymentItemsDTO> items) {
		super();
		this.pay_seq = pay_seq;
		this.doc_seq = doc_seq;
		this.pay_date = pay_date;
		this.total_amount = total_amount;
		this.pay_reason = pay_reason;
		this.bank_name = bank_name;
		this.account_holder = account_holder;
		this.account_number = account_number;
		this.approvers = approvers;
		this.referrers = referrers;
		this.items = items;
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
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getAccount_holder() {
		return account_holder;
	}
	public void setAccount_holder(String account_holder) {
		this.account_holder = account_holder;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
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
	public List<PaymentItemsDTO> getItems() {
		return items;
	}
	public void setItems(List<PaymentItemsDTO> items) {
		this.items = items;
	}
}
