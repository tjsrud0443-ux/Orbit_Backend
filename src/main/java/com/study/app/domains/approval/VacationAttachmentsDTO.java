package com.study.app.domains.approval;

public class VacationAttachmentsDTO {
	private Long attachment_seq;
	private Long vac_seq;
	private String oriname;
	private String sysname;
	private String created_at;
	
	public VacationAttachmentsDTO() {}
	public VacationAttachmentsDTO(Long attachment_seq, Long vac_seq, String oriname, String sysname,
			String created_at) {
		super();
		this.attachment_seq = attachment_seq;
		this.vac_seq = vac_seq;
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
	public Long getVac_seq() {
		return vac_seq;
	}
	public void setVac_seq(Long vac_seq) {
		this.vac_seq = vac_seq;
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
