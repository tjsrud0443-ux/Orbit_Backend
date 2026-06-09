package com.study.app.domains.board;

public class BoardCommentsDTO {
    private Long comment_seq;
    private Long post_seq;
    private String users_id;
    private String content;
    private String created_at;
    private String update_at;
    //DB엔 없
    private String author_name;
    private String author_sysname; 
    
    public BoardCommentsDTO() {}

	public BoardCommentsDTO(Long comment_seq, Long post_seq, String users_id, String content, String created_at,
			String update_at, String author_name, String author_sysname) {
		super();
		this.comment_seq = comment_seq;
		this.post_seq = post_seq;
		this.users_id = users_id;
		this.content = content;
		this.created_at = created_at;
		this.update_at = update_at;
		this.author_name = author_name;
		this.author_sysname = author_sysname;
	}

	public Long getComment_seq() {
		return comment_seq;
	}

	public void setComment_seq(Long comment_seq) {
		this.comment_seq = comment_seq;
	}

	public Long getPost_seq() {
		return post_seq;
	}

	public void setPost_seq(Long post_seq) {
		this.post_seq = post_seq;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAuthor_sysname() {
		return author_sysname;
	}

	public void setAuthor_sysname(String author_sysname) {
		this.author_sysname = author_sysname;
	}

}
