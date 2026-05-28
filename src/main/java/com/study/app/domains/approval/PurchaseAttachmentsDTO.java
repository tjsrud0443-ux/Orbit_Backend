package com.study.app.domains.approval;

public class PurchaseAttachmentsDTO {
	private Long attachment_seq;
	private Long purchase_seq;
	private String oriname;
	private String sysname;
	private String created_at;
	
	public PurchaseAttachmentsDTO() {}
	public PurchaseAttachmentsDTO(Long attachment_seq, Long purchase_seq, String oriname, String sysname,
			String created_at) {
		super();
		this.attachment_seq = attachment_seq;
		this.purchase_seq = purchase_seq;
		this.oriname = oriname;
		this.sysname = sysname;
		this.created_at = created_at;
	}
	public Long getAttachment_seq() {
		return attachment_seq;
	}
	public void setAttachment_seq(Long attachment_seq) {
		this.attachment_seq = attachment_seq;
	}
	public Long getPurchase_seq() {
		return purchase_seq;
	}
	public void setPurchase_seq(Long purchase_seq) {
		this.purchase_seq = purchase_seq;
	}
	public String getOriname() {
		return oriname;
	}
	public void setOriname(String oriname) {
		this.oriname = oriname;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
