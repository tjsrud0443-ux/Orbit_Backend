package com.study.app.domains.documents;

public class DocumentBookmarksDTO {
	private Long bookmark_seq;
	private String users_id;
	private Long document_seq;
	private String created_at;
	
	public DocumentBookmarksDTO() {}
	public DocumentBookmarksDTO(Long bookmark_seq, String users_id, Long document_seq, String created_at) {
		super();
		this.bookmark_seq = bookmark_seq;
		this.users_id = users_id;
		this.document_seq = document_seq;
		this.created_at = created_at;
	}
	public Long getBookmark_seq() {
		return bookmark_seq;
	}
	public void setBookmark_seq(Long bookmark_seq) {
		this.bookmark_seq = bookmark_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public Long getDocument_seq() {
		return document_seq;
	}
	public void setDocument_seq(Long document_seq) {
		this.document_seq = document_seq;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
