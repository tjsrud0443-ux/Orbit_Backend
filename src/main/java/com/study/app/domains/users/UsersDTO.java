package com.study.app.domains.users;

public class UsersDTO {
	
	private String id;
	private String pw;
	private Long users_seq;
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
	private String role;
	private String status;
	private String hire_date;
	private String resign_date;
	private Long dept_seq;
	private Long rank_seq;
	private String created_at;
	private String update_at;
	private String sysname;
	private String is_hr_manager;
	
	private String dept_name;
    private String rank_name;
    private Long rank_order;
    private String auth_group;
    private Long parent_dept_seq;
    
    private String stamp_sysname;
    private String stamp_oriname;
    private Long stamp_seq;
    
    private Double remaining_days;
    
    private String user_auth_group;
    
    private String profile_url;
	
	public UsersDTO() {}

	public UsersDTO(String id, String pw, Long users_seq, String name, String phone, String email, String oriname,
			String ssn, String ssn_hash, String ssn_enc, String ssn_masked, String zonecode, String address1,
			String address2, String role, String status, String hire_date, String resign_date, Long dept_seq,
			Long rank_seq, String created_at, String update_at, String sysname, String is_hr_manager, String dept_name,
			String rank_name, Long rank_order, String auth_group, Long parent_dept_seq, String stamp_sysname,
			String stamp_oriname, Long stamp_seq, Double remaining_days, String user_auth_group, String profile_url) {
		super();
		this.id = id;
		this.pw = pw;
		this.users_seq = users_seq;
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
		this.role = role;
		this.status = status;
		this.hire_date = hire_date;
		this.resign_date = resign_date;
		this.dept_seq = dept_seq;
		this.rank_seq = rank_seq;
		this.created_at = created_at;
		this.update_at = update_at;
		this.sysname = sysname;
		this.is_hr_manager = is_hr_manager;
		this.dept_name = dept_name;
		this.rank_name = rank_name;
		this.rank_order = rank_order;
		this.auth_group = auth_group;
		this.parent_dept_seq = parent_dept_seq;
		this.stamp_sysname = stamp_sysname;
		this.stamp_oriname = stamp_oriname;
		this.stamp_seq = stamp_seq;
		this.remaining_days = remaining_days;
		this.user_auth_group = user_auth_group;
		this.profile_url = profile_url;
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

	public Long getUsers_seq() {
		return users_seq;
	}

	public void setUsers_seq(Long users_seq) {
		this.users_seq = users_seq;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHire_date() {
		return hire_date;
	}

	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}

	public String getResign_date() {
		return resign_date;
	}

	public void setResign_date(String resign_date) {
		this.resign_date = resign_date;
	}

	public Long getDept_seq() {
		return dept_seq;
	}

	public void setDept_seq(Long dept_seq) {
		this.dept_seq = dept_seq;
	}

	public Long getRank_seq() {
		return rank_seq;
	}

	public void setRank_seq(Long rank_seq) {
		this.rank_seq = rank_seq;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	public String getSysname() {
		return sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	public String getIs_hr_manager() {
		return is_hr_manager;
	}

	public void setIs_hr_manager(String is_hr_manager) {
		this.is_hr_manager = is_hr_manager;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getRank_name() {
		return rank_name;
	}

	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}

	public Long getRank_order() {
		return rank_order;
	}

	public void setRank_order(Long rank_order) {
		this.rank_order = rank_order;
	}

	public String getAuth_group() {
		return auth_group;
	}

	public void setAuth_group(String auth_group) {
		this.auth_group = auth_group;
	}

	public Long getParent_dept_seq() {
		return parent_dept_seq;
	}

	public void setParent_dept_seq(Long parent_dept_seq) {
		this.parent_dept_seq = parent_dept_seq;
	}

	public String getStamp_sysname() {
		return stamp_sysname;
	}

	public void setStamp_sysname(String stamp_sysname) {
		this.stamp_sysname = stamp_sysname;
	}

	public String getStamp_oriname() {
		return stamp_oriname;
	}

	public void setStamp_oriname(String stamp_oriname) {
		this.stamp_oriname = stamp_oriname;
	}

	public Long getStamp_seq() {
		return stamp_seq;
	}

	public void setStamp_seq(Long stamp_seq) {
		this.stamp_seq = stamp_seq;
	}

	public Double getRemaining_days() {
		return remaining_days;
	}

	public void setRemaining_days(Double remaining_days) {
		this.remaining_days = remaining_days;
	}

	public String getUser_auth_group() {
		return user_auth_group;
	}

	public void setUser_auth_group(String user_auth_group) {
		this.user_auth_group = user_auth_group;
	}

	public String getProfile_url() {
		return profile_url;
	}

	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}

}