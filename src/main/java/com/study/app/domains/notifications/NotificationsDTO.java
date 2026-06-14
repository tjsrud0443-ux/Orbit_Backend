package com.study.app.domains.notifications;

public class NotificationsDTO {
	
	private Long noti_seq;
	private String users_id;
	private String noti_type;
	private String content;
	private String ref_type;
	private Long ref_seq;
	private String created_at;
	private String read_yn;
	
	public NotificationsDTO() {}
	
	public NotificationsDTO(Long noti_seq, String users_id, String noti_type, String content, String ref_type,
			Long ref_seq, String created_at, String read_yn) {
		super();
		this.noti_seq = noti_seq;
		this.users_id = users_id;
		this.noti_type = noti_type;
		this.content = content;
		this.ref_type = ref_type;
		this.ref_seq = ref_seq;
		this.created_at = created_at;
		this.read_yn = read_yn;
	}
	
	public Long getNoti_seq() {
		return noti_seq;
	}
	public void setNoti_seq(Long noti_seq) {
		this.noti_seq = noti_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getNoti_type() {
		return noti_type;
	}
	public void setNoti_type(String noti_type) {
		this.noti_type = noti_type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRef_type() {
		return ref_type;
	}
	public void setRef_type(String ref_type) {
		this.ref_type = ref_type;
	}
	public Long getRef_seq() {
		return ref_seq;
	}
	public void setRef_seq(Long ref_seq) {
		this.ref_seq = ref_seq;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getRead_yn() {
		return read_yn;
	}
	public void setRead_yn(String read_yn) {
		this.read_yn = read_yn;
	}
}