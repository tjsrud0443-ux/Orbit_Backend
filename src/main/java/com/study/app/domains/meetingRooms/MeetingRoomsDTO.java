package com.study.app.domains.meetingRooms;

public class MeetingRoomsDTO {
	private Long room_seq;
	private String room_name;
	private Long max_people;
	private String oriname;
	private String sysname;
	private String room_floor;
	
	public MeetingRoomsDTO() {}
	public MeetingRoomsDTO(Long room_seq, String room_name, Long max_people, String oriname, String sysname,
			String room_floor) {
		super();
		this.room_seq = room_seq;
		this.room_name = room_name;
		this.max_people = max_people;
		this.oriname = oriname;
		this.sysname = sysname;
		this.room_floor = room_floor;
	}
	public Long getRoom_seq() {
		return room_seq;
	}
	public void setRoom_seq(Long room_seq) {
		this.room_seq = room_seq;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public Long getMax_people() {
		return max_people;
	}
	public void setMax_people(Long max_people) {
		this.max_people = max_people;
	}
	public String getOriname() {
		return oriname;
	}
	public void setOriname(String oriname) {
		this.oriname = oriname;
	}
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	public String getRoom_floor() {
		return room_floor;
	}
	public void setRoom_floor(String room_floor) {
		this.room_floor = room_floor;
	}
}
