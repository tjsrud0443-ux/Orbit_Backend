package com.study.app.domains.pageInfo;

public class PageInfoDTO {
	private Long page_seq;
	private String page_code;
	private String page_name;
	private String page_info;
	private String page_category;
	
	public PageInfoDTO() {}
	public PageInfoDTO(Long page_seq, String page_code, String page_name, String page_info, String page_category) {
		super();
		this.page_seq = page_seq;
		this.page_code = page_code;
		this.page_name = page_name;
		this.page_info = page_info;
		this.page_category = page_category;
	}
	public Long getPage_seq() {
		return page_seq;
	}
	public void setPage_seq(Long page_seq) {
		this.page_seq = page_seq;
	}
	public String getPage_code() {
		return page_code;
	}
	public void setPage_code(String page_code) {
		this.page_code = page_code;
	}
	public String getPage_name() {
		return page_name;
	}
	public void setPage_name(String page_name) {
		this.page_name = page_name;
	}
	public String getPage_info() {
		return page_info;
	}
	public void setPage_info(String page_info) {
		this.page_info = page_info;
	}
	public String getPage_category() {
		return page_category;
	}
	public void setPage_category(String page_category) {
		this.page_category = page_category;
	}
}
