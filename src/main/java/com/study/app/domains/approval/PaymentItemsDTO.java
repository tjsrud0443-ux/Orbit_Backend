package com.study.app.domains.approval;

public class PaymentItemsDTO {
	private Long item_seq;
	private Long pay_seq;
	private Long item_order;
	private String item_name;
	private Double amount;
	private String note;
	private String sysname;
	private String oriname;
	
	public PaymentItemsDTO() {}
	public PaymentItemsDTO(Long item_seq, Long pay_seq, Long item_order, String item_name, Double amount, String note,
			String sysname, String oriname) {
		super();
		this.item_seq = item_seq;
		this.pay_seq = pay_seq;
		this.item_order = item_order;
		this.item_name = item_name;
		this.amount = amount;
		this.note = note;
		this.sysname = sysname;
		this.oriname = oriname;
	}
	public Long getItem_seq() {
		return item_seq;
	}
	public void setItem_seq(Long item_seq) {
		this.item_seq = item_seq;
	}
	public Long getPay_seq() {
		return pay_seq;
	}
	public void setPay_seq(Long pay_seq) {
		this.pay_seq = pay_seq;
	}
	public Long getItem_order() {
		return item_order;
	}
	public void setItem_order(Long item_order) {
		this.item_order = item_order;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	public String getOriname() {
		return oriname;
	}
	public void setOriname(String oriname) {
		this.oriname = oriname;
	}
}
