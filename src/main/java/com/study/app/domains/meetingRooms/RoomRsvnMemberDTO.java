package com.study.app.domains.meetingRooms;

public class RoomRsvnMemberDTO {
	private Long rm_seq;
	private Long rsvn_seq;
	private String users_id;
	
	public RoomRsvnMemberDTO() {}
	public RoomRsvnMemberDTO(Long rm_seq, Long rsvn_seq, String users_id) {
		super();
		this.rm_seq = rm_seq;
		this.rsvn_seq = rsvn_seq;
		this.users_id = users_id;
	}
	public Long getRm_seq() {
		return rm_seq;
	}
	public void setRm_seq(Long rm_seq) {
		this.rm_seq = rm_seq;
	}
	public Long getRsvn_seq() {
		return rsvn_seq;
	}
	public void setRsvn_seq(Long rsvn_seq) {
		this.rsvn_seq = rsvn_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
}
