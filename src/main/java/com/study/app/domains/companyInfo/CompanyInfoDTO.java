package com.study.app.domains.companyInfo;

public class CompanyInfoDTO {
	
	private Long companySeq;
	private String companyName;
	private String ceoName;
	private String businessNumber;
	private String companyTel;
	private String companyEmail;
	private String companyAddress;
	private String companyDetailAddr;
	private String companyFax;
	private String created_at;
	private String updated_at;
	private String updated_id;
	private String companyZonecode;
	
	public CompanyInfoDTO() {}
	
	public CompanyInfoDTO(Long companySeq, String companyName, String ceoName, String businessNumber, String companyTel,
			String companyEmail, String companyAddress, String companyDetailAddr, String companyFax, String created_at,
			String updated_at, String updated_id, String companyZonecode) {
		super();
		this.companySeq = companySeq;
		this.companyName = companyName;
		this.ceoName = ceoName;
		this.businessNumber = businessNumber;
		this.companyTel = companyTel;
		this.companyEmail = companyEmail;
		this.companyAddress = companyAddress;
		this.companyDetailAddr = companyDetailAddr;
		this.companyFax = companyFax;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.updated_id = updated_id;
		this.companyZonecode = companyZonecode;
	}

	public Long getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(Long companySeq) {
		this.companySeq = companySeq;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCeoName() {
		return ceoName;
	}

	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}

	public String getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyDetailAddr() {
		return companyDetailAddr;
	}

	public void setCompanyDetailAddr(String companyDetailAddr) {
		this.companyDetailAddr = companyDetailAddr;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getUpdated_id() {
		return updated_id;
	}

	public void setUpdated_id(String updated_id) {
		this.updated_id = updated_id;
	}

	public String getCompanyZonecode() {
		return companyZonecode;
	}

	public void setCompanyZonecode(String companyZonecode) {
		this.companyZonecode = companyZonecode;
	}
}