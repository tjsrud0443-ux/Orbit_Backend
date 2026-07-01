package com.study.app.domains.notifications;

public class NotificationsDeleteEventDTO {

	private String eventType;
	private Long noti_seq;
	private String users_id;
	private String ref_type;
	private Long ref_seq;
	
	public NotificationsDeleteEventDTO() {}
	
	public NotificationsDeleteEventDTO(String eventType, Long noti_seq, String users_id, String ref_type,
			Long ref_seq) {
		super();
		this.eventType = eventType;
		this.noti_seq = noti_seq;
		this.users_id = users_id;
		this.ref_type = ref_type;
		this.ref_seq = ref_seq;
	}
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
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
}