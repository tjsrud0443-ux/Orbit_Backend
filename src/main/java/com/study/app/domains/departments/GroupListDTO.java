package com.study.app.domains.departments;

// 목록형 조직도 DTO
public class GroupListDTO {
	
	private String id;
	private String name;
	private Long deptSeq;
	private String deptCode;
	private String deptName;
	private String sysname;
	private String position;
	private String phone;
	private String attendanceStatus;
	private Long rankSeq;
	private Long rankOrder;
	
	public GroupListDTO() {}
	
	public GroupListDTO(String id, String name, Long deptSeq, String deptCode, String deptName, String sysname, String position, String phone,
			String attendanceStatus, Long rankSeq, Long rankOrder) {
		super();
		this.id = id;
		this.name = name;
		this.deptSeq = deptSeq;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.sysname = sysname;
		this.position = position;
		this.phone = phone;
		this.attendanceStatus = attendanceStatus;
		this.rankSeq = rankSeq;
		this.rankOrder = rankOrder;
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
	public Long getDeptSeq() {
		return deptSeq;
	}
	public void setDeptSeq(Long deptSeq) {
		this.deptSeq = deptSeq;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAttendanceStatus() {
		return attendanceStatus;
	}
	public void setAttendanceStatus(String attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
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
}