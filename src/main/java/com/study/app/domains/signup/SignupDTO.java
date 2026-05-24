package com.study.app.domains.signup;

public class SignupDTO {
	
	private Long signup_seq;
	private String id;
	private String pw;
	private String name;
	private String phone;
	private String email;
	private String oriname;
	private String ssn;
	private String ssn_hash;
	private String ssn_enc;
	private String ssn_masked;
	private String zonecode;
	private String address1;
	private String address2;
	private String status;
	private String signup_at;
	private String sysname;
	
	public SignupDTO() {}
	public SignupDTO(Long signup_seq, String id, String pw, String name, String phone, String email, String oriname,
			String ssn, String ssn_hash, String ssn_enc, String ssn_masked, String zonecode, String address1,
			String address2, String status, String signup_at, String sysname) {
		super();
		this.signup_seq = signup_seq;
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.oriname = oriname;
		this.ssn = ssn;
		this.ssn_hash = ssn_hash;
		this.ssn_enc = ssn_enc;
		this.ssn_masked = ssn_masked;
		this.zonecode = zonecode;
		this.address1 = address1;
		this.address2 = address2;
		this.status = status;
		this.signup_at = signup_at;
		this.sysname = sysname;
	}
	public Long getSignup_seq() {
		return signup_seq;
	}
	public void setSignup_seq(Long signup_seq) {
		this.signup_seq = signup_seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOriname() {
		return oriname;
	}
	public void setOriname(String oriname) {
		this.oriname = oriname;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getSsn_hash() {
		return ssn_hash;
	}
	public void setSsn_hash(String ssn_hash) {
		this.ssn_hash = ssn_hash;
	}
	public String getSsn_enc() {
		return ssn_enc;
	}
	public void setSsn_enc(String ssn_enc) {
		this.ssn_enc = ssn_enc;
	}
	public String getSsn_masked() {
		return ssn_masked;
	}
	public void setSsn_masked(String ssn_masked) {
		this.ssn_masked = ssn_masked;
	}
	public String getZonecode() {
		return zonecode;
	}
	public void setZonecode(String zonecode) {
		this.zonecode = zonecode;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSignup_at() {
		return signup_at;
	}
	public void setSignup_at(String signup_at) {
		this.signup_at = signup_at;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
}
