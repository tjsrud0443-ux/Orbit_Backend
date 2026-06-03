package com.study.app.domains.meetingMinutes;

import java.time.LocalDateTime;

public class MeetingMinutesDTO {
	private Long minute_seq;
    private String title;
    private LocalDateTime meetingDt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String mainContent;
    private String decisions;
    private String todos;
    private String usersId;
    private LocalDateTime createdAt;
    
	public MeetingMinutesDTO() {}

	public MeetingMinutesDTO(Long minute_seq, String title, LocalDateTime meetingDt, LocalDateTime startTime,
			LocalDateTime endTime, String mainContent, String decisions, String todos, String usersId,
			LocalDateTime createdAt) {
		super();
		this.minute_seq = minute_seq;
		this.title = title;
		this.meetingDt = meetingDt;
		this.startTime = startTime;
		this.endTime = endTime;
		this.mainContent = mainContent;
		this.decisions = decisions;
		this.todos = todos;
		this.usersId = usersId;
		this.createdAt = createdAt;
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

	public LocalDateTime getMeetingDt() {
		return meetingDt;
	}

	public void setMeetingDt(LocalDateTime meetingDt) {
		this.meetingDt = meetingDt;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getMainContent() {
		return mainContent;
	}

	public void setMainContent(String mainContent) {
		this.mainContent = mainContent;
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

	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
    
}

