package com.study.app.domains.aiChat;

public class AiChatDTO {
	
	private Long chat_seq;
	private String users_id;
	private String title;
	private String created_at;
	private String updated_at;
	
	public AiChatDTO() {}
	
	public AiChatDTO(Long chat_seq, String users_id, String title, String created_at, String updated_at) {
		super();
		this.chat_seq = chat_seq;
		this.users_id = users_id;
		this.title = title;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public Long getChat_seq() {
		return chat_seq;
	}
	public void setChat_seq(Long chat_seq) {
		this.chat_seq = chat_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
}