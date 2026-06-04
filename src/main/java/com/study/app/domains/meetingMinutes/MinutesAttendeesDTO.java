package com.study.app.domains.meetingMinutes;

public class MinutesAttendeesDTO {
	 private Long attendee_seq;
	 private Long minute_seq;
	 private String users_id;
	 private String name;
	 private String sysname;
    
    public MinutesAttendeesDTO() {}

	public MinutesAttendeesDTO(Long attendee_seq, Long minute_seq, String users_id, String name, String sysname) {
		super();
		this.attendee_seq = attendee_seq;
		this.minute_seq = minute_seq;
		this.users_id = users_id;
		this.name = name;
		this.sysname = sysname;
	}

	public Long getAttendee_seq() {
		return attendee_seq;
	}

	public void setAttendee_seq(Long attendee_seq) {
		this.attendee_seq = attendee_seq;
	}

	public Long getMinute_seq() {
		return minute_seq;
	}

	public void setMinute_seq(Long minute_seq) {
		this.minute_seq = minute_seq;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSysname() {
		return sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

}
