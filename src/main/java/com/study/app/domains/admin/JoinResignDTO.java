package com.study.app.domains.admin;

public class JoinResignDTO {
	
	private String month;
	private Long joinCount;
	private Long resignCount;
	
	public JoinResignDTO() {}
	
	public JoinResignDTO(String month, Long joinCount, Long resignCount) {
		super();
		this.month = month;
		this.joinCount = joinCount;
		this.resignCount = resignCount;
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Long getJoinCount() {
		return joinCount;
	}
	public void setJoinCount(Long joinCount) {
		this.joinCount = joinCount;
	}
	public Long getResignCount() {
		return resignCount;
	}
	public void setResignCount(Long resignCount) {
		this.resignCount = resignCount;
	}
}
