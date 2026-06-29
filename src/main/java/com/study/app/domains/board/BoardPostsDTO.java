package com.study.app.domains.board;

import java.time.LocalDateTime;
import java.util.List;

public class BoardPostsDTO {
	private Long post_seq;       // POST_SEQ (NUMBER)
    private String title;        // TITLE (VARCHAR2)
    private String category;     // CATEGORY (VARCHAR2)
    private String content;      // CONTENT (CLOB)
    private String users_id;     // USERS_ID (VARCHAR2)
    private int view_count;      // VIEW_COUNT (NUMBER)
    private LocalDateTime created_at;     // CREATED_AT (DATE)
    private String author_name;
    private String author_sysname;  
    
    private long row_rank;
    
    private List<BoardFileDTO> files;
    private List<BoardCommentsDTO> comments;
    
    public BoardPostsDTO() {}

	public BoardPostsDTO(Long post_seq, String title, String category, String content, String users_id, int view_count,
			LocalDateTime created_at, String author_name, String author_sysname, long row_rank,
			List<BoardFileDTO> files, List<BoardCommentsDTO> comments) {
		super();
		this.post_seq = post_seq;
		this.title = title;
		this.category = category;
		this.content = content;
		this.users_id = users_id;
		this.view_count = view_count;
		this.created_at = created_at;
		this.author_name = author_name;
		this.author_sysname = author_sysname;
		this.row_rank = row_rank;
		this.files = files;
		this.comments = comments;
	}

	public Long getPost_seq() {
		return post_seq;
	}

	public void setPost_seq(Long post_seq) {
		this.post_seq = post_seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public int getView_count() {
		return view_count;
	}

	public void setView_count(int view_count) {
		this.view_count = view_count;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
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

	public long getRow_rank() {
		return row_rank;
	}

	public void setRow_rank(long row_rank) {
		this.row_rank = row_rank;
	}

	public List<BoardFileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<BoardFileDTO> files) {
		this.files = files;
	}

	public List<BoardCommentsDTO> getComments() {
		return comments;
	}

	public void setComments(List<BoardCommentsDTO> comments) {
		this.comments = comments;
	}

	
    
}
