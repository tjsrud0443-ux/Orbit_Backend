package com.study.app.domains.approval;

public class PurchaseItemsDTO {
	private Long item_seq;
	private Long purchase_seq;
	private Long item_order;
	private String item_name;
	private Long ea;
	private Double unit_price;
	private Double total_price;
	private String note;
	
	public PurchaseItemsDTO() {}
	public PurchaseItemsDTO(Long item_seq, Long purchase_seq, Long item_order, String item_name, Long ea,
			Double unit_price, Double total_price, String note) {
		super();
		this.item_seq = item_seq;
		this.purchase_seq = purchase_seq;
		this.item_order = item_order;
		this.item_name = item_name;
		this.ea = ea;
		this.unit_price = unit_price;
		this.total_price = total_price;
		this.note = note;
	}
	public Long getItem_seq() {
		return item_seq;
	}
	public void setItem_seq(Long item_seq) {
		this.item_seq = item_seq;
	}
	public Long getPurchase_seq() {
		return purchase_seq;
	}
	public void setPurchase_seq(Long purchase_seq) {
		this.purchase_seq = purchase_seq;
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
	public Long getEa() {
		return ea;
	}
	public void setEa(Long ea) {
		this.ea = ea;
	}
	public Double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}
	public Double getTotal_price() {
		return total_price;
	}
	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
