package com.study.app.domains.schedules;

import java.time.LocalDateTime;

public class SchedulesDTO {
	private Long schedule_seq;
    private String title;
    private String schedule_type;
    private String users_id;
    private String start_dt;
    private String end_dt;
    private String sked_reason;
    private Integer is_public;
    private Long ref_seq;
    private String ref_type;
    private String created_at;
    
    public SchedulesDTO() {}
	public SchedulesDTO(Long schedule_seq, String title, String schedule_type, String users_id, String start_dt,
			String end_dt, String sked_reason, Integer is_public, Long ref_seq, String ref_type, String created_at) {
		super();
		this.schedule_seq = schedule_seq;
		this.title = title;
		this.schedule_type = schedule_type;
		this.users_id = users_id;
		this.start_dt = start_dt;
		this.end_dt = end_dt;
		this.sked_reason = sked_reason;
		this.is_public = is_public;
		this.ref_seq = ref_seq;
		this.ref_type = ref_type;
		this.created_at = created_at;
	}
	public Long getSchedule_seq() {
		return schedule_seq;
	}
	public void setSchedule_seq(Long schedule_seq) {
		this.schedule_seq = schedule_seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSchedule_type() {
		return schedule_type;
	}
	public void setSchedule_type(String schedule_type) {
		this.schedule_type = schedule_type;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getStart_dt() {
		return start_dt;
	}
	public void setStart_dt(String start_dt) {
		this.start_dt = start_dt;
	}
	public String getEnd_dt() {
		return end_dt;
	}
	public void setEnd_dt(String end_dt) {
		this.end_dt = end_dt;
	}
	public String getSked_reason() {
		return sked_reason;
	}
	public void setSked_reason(String sked_reason) {
		this.sked_reason = sked_reason;
	}
	public Integer getIs_public() {
		return is_public;
	}
	public void setIs_public(Integer is_public) {
		this.is_public = is_public;
	}
	public Long getRef_seq() {
		return ref_seq;
	}
	public void setRef_seq(Long ref_seq) {
		this.ref_seq = ref_seq;
	}
	public String getRef_type() {
		return ref_type;
	}
	public void setRef_type(String ref_type) {
		this.ref_type = ref_type;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
