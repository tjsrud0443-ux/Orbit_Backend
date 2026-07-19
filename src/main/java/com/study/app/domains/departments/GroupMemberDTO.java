package com.study.app.domains.departments;

public class GroupMemberDTO {
	private String id;
	private String name;
	private String position;
	private String sysname;
	private String phone;
	private String email;
	private Long rankSeq;
	private Long rankOrder;
	private Long deptSeq;
	
	public GroupMemberDTO() {}
	
	public GroupMemberDTO(String id, String name, String position, String sysname,
			String phone,String email, Long rankSeq, Long rankOrder, Long deptSeq) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.sysname = sysname;
		this.phone = phone;
		this.email = email;
		this.rankSeq = rankSeq;
		this.rankOrder = rankOrder;
		this.deptSeq = deptSeq;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
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
	public Long getRankSeq() {
		return rankSeq;
	}
	public void setRankSeq(Long rankSeq) {
		this.rankSeq = rankSeq;
	}
	public Long getRankOrder() {
		return rankOrder;
	}
	public void setRankOrder(Long rankOrder) {
		this.rankOrder = rankOrder;
	}
	public Long getDeptSeq() {
		return deptSeq;
	}
	public void setDeptSeq(Long deptSeq) {
		this.deptSeq = deptSeq;
	}
}