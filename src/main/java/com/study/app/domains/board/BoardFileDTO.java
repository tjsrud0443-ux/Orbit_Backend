package com.study.app.domains.board;

import java.time.LocalDateTime;

public class BoardFileDTO {
	private Long file_seq;       // FILE_SEQ (NUMBER)
    private Long post_seq;       // POST_SEQ (NUMBER)
    private String file_oriname; // FILE_ORINAME (VARCHAR2)
    private String file_sysname; // FILE_SYSNAME (VARCHAR2)
    private String file_path;    // FILE_PATH (VARCHAR2)
    private Long file_size;      // FILE_SIZE (NUMBER)
    private LocalDateTime upload_at;      // UPLOAD_AT (DATE)
    
    public BoardFileDTO() {}
	public BoardFileDTO(Long file_seq, Long post_seq, String file_oriname, String file_sysname, String file_path,
			Long file_size, LocalDateTime upload_at) {
		super();
		this.file_seq = file_seq;
		this.post_seq = post_seq;
		this.file_oriname = file_oriname;
		this.file_sysname = file_sysname;
		this.file_path = file_path;
		this.file_size = file_size;
		this.upload_at = upload_at;
	}
	public Long getFile_seq() {
		return file_seq;
	}
	public void setFile_seq(Long file_seq) {
		this.file_seq = file_seq;
	}
	public Long getPost_seq() {
		return post_seq;
	}
	public void setPost_seq(Long post_seq) {
		this.post_seq = post_seq;
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
	public Long getFile_size() {
		return file_size;
	}
	public void setFile_size(Long file_size) {
		this.file_size = file_size;
	}
	public LocalDateTime getUpload_at() {
		return upload_at;
	}
	public void setUpload_at(LocalDateTime upload_at) {
		this.upload_at = upload_at;
	}
    
    
}
