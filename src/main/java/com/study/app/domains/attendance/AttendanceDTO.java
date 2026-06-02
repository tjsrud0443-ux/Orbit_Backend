package com.study.app.domains.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceDTO {
	private Long attendance_seq;
	private String users_id;
    private LocalDate work_date;     
    private LocalDateTime check_in;     
    private LocalDateTime check_out;    
    private Integer total_work_in;      // NUMBER → Integer (null 가능)
 // DB 컬럼에 없음
    private int work_days;
    private double total_hours;
    private int late_cnt;
    
    public AttendanceDTO() {}

	public AttendanceDTO(Long attendance_seq, String users_id, LocalDate work_date, LocalDateTime check_in,
			LocalDateTime check_out, Integer total_work_in, int work_days, double total_hours, int late_cnt) {
		super();
		this.attendance_seq = attendance_seq;
		this.users_id = users_id;
		this.work_date = work_date;
		this.check_in = check_in;
		this.check_out = check_out;
		this.total_work_in = total_work_in;
		this.work_days = work_days;
		this.total_hours = total_hours;
		this.late_cnt = late_cnt;
	}

	public int getWork_days() {
		return work_days;
	}

	public void setWork_days(int work_days) {
		this.work_days = work_days;
	}

	public double getTotal_hours() {
		return total_hours;
	}

	public void setTotal_hours(double total_hours) {
		this.total_hours = total_hours;
	}

	public int getLate_cnt() {
		return late_cnt;
	}

	public void setLate_cnt(int late_cnt) {
		this.late_cnt = late_cnt;
	}

	public Long getAttendance_seq() {
		return attendance_seq;
	}

	public void setAttendance_seq(Long attendance_seq) {
		this.attendance_seq = attendance_seq;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public LocalDate getWork_date() {
		return work_date;
	}

	public void setWork_date(LocalDate work_date) {
		this.work_date = work_date;
	}

	public LocalDateTime getCheck_in() {
		return check_in;
	}

	public void setCheck_in(LocalDateTime check_in) {
		this.check_in = check_in;
	}

	public LocalDateTime getCheck_out() {
		return check_out;
	}

	public void setCheck_out(LocalDateTime check_out) {
		this.check_out = check_out;
	}

	public Integer getTotal_work_in() {
		return total_work_in;
	}

	public void setTotal_work_in(Integer total_work_in) {
		this.total_work_in = total_work_in;
	}
	
    
}
