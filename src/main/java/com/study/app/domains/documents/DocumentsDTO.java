package com.study.app.domains.documents;

public class DocumentsDTO {
	private Long document_seq;
	private String title;
	private String users_id;
	private String created_at;
	private Long file_seq;
	private String file_oriname;
	private String file_sysname;
	private String file_path;
	private String mime_type;
	private String upload_at;
	
	private String name;
	
	public DocumentsDTO() {}
	public DocumentsDTO(Long document_seq, String title, String users_id, String created_at, Long file_seq,
			String file_oriname, String file_sysname, String file_path, String mime_type, String upload_at,
			String name) {
		super();
		this.document_seq = document_seq;
		this.title = title;
		this.users_id = users_id;
		this.created_at = created_at;
		this.file_seq = file_seq;
		this.file_oriname = file_oriname;
		this.file_sysname = file_sysname;
		this.file_path = file_path;
		this.mime_type = mime_type;
		this.upload_at = upload_at;
		this.name = name;
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
	public Long getFile_seq() {
		return file_seq;
	}
	public void setFile_seq(Long file_seq) {
		this.file_seq = file_seq;
	}
	public String getFile_oriname() {
		return file_oriname;
	}
	public void setFile_oriname(String file_oriname) {
		this.file_oriname = file_oriname;
	}
	public String getFile_sysname() {
		return file_sysname;
	}
	public void setFile_sysname(String file_sysname) {
		this.file_sysname = file_sysname;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public String getMime_type() {
		return mime_type;
	}
	public void setMime_type(String mime_type) {
		this.mime_type = mime_type;
	}
	public String getUpload_at() {
		return upload_at;
	}
	public void setUpload_at(String upload_at) {
		this.upload_at = upload_at;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
