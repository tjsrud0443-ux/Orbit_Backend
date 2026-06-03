package com.study.app.domains.meetingMinutes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MeetingMinutesDTO {
    private Long minute_seq;
    private String title;
    private LocalDate meeting_dt;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String main_content;
    private String decisions;
    private String todos;
    private String users_id;
    private LocalDateTime created_at;
    private List<MinutesAttendeesDTO> attendees;
    
	public MeetingMinutesDTO() {}

	public MeetingMinutesDTO(Long minute_seq, String title, LocalDate meeting_dt, LocalDateTime start_time,
			LocalDateTime end_time, String main_content, String decisions, String todos, String users_id,
			LocalDateTime created_at, List<MinutesAttendeesDTO> attendees) {
		super();
		this.minute_seq = minute_seq;
		this.title = title;
		this.meeting_dt = meeting_dt;
		this.start_time = start_time;
		this.end_time = end_time;
		this.main_content = main_content;
		this.decisions = decisions;
		this.todos = todos;
		this.users_id = users_id;
		this.created_at = created_at;
		this.attendees = attendees;
	}

	public Long getMinute_seq() {
		return minute_seq;
	}

	public void setMinute_seq(Long minute_seq) {
		this.minute_seq = minute_seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getMeeting_dt() {
		return meeting_dt;
	}

	public void setMeeting_dt(LocalDate meeting_dt) {
		this.meeting_dt = meeting_dt;
	}

	public LocalDateTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalDateTime start_time) {
		this.start_time = start_time;
	}

	public LocalDateTime getEnd_time() {
		return end_time;
	}

	public void setEnd_time(LocalDateTime end_time) {
		this.end_time = end_time;
	}

	public String getMain_content() {
		return main_content;
	}

	public void setMain_content(String main_content) {
		this.main_content = main_content;
	}

	public String getDecisions() {
		return decisions;
	}

	public void setDecisions(String decisions) {
		this.decisions = decisions;
	}

	public String getTodos() {
		return todos;
	}

	public void setTodos(String todos) {
		this.todos = todos;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public List<MinutesAttendeesDTO> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<MinutesAttendeesDTO> attendees) {
		this.attendees = attendees;
	}

	
}

