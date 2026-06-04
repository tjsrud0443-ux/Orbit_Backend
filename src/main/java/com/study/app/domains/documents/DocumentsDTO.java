package com.study.app.domains.documents;

public class DocumentsDTO {
	private Long document_seq;
	private String title;
	private String users_id;
	private String created_at;
	private DocumentsFilesDTO file;
	
	public DocumentsDTO() {}
	public DocumentsDTO(Long document_seq, String title, String users_id, String created_at) {
		super();
		this.document_seq = document_seq;
		this.title = title;
		this.users_id = users_id;
		this.created_at = created_at;
	}
	public Long getDocument_seq() {
		return document_seq;
	}
	public void setDocument_seq(Long document_seq) {
		this.document_seq = document_seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public DocumentsFilesDTO getFile() { 
		return file; 
	}
    public void setFile(DocumentsFilesDTO file) { 
    	this.file = file;
    }
}
