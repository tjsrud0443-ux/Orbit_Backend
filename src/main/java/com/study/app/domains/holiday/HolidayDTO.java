package com.study.app.domains.holiday;

public class HolidayDTO {
	private Long holiday_seq;   
    private Integer holiday_year; 
    private String locdate;     // TIMESTAMP(6) → 'YYYY-MM-DD' 문자열로 주고받음
    private String date_name;   
    private String is_holiday;  
    private String created_at;
    
    public HolidayDTO() {}
	public HolidayDTO(Long holiday_seq, Integer holiday_year, String locdate, String date_name, String is_holiday,
			String created_at) {
		super();
		this.holiday_seq = holiday_seq;
		this.holiday_year = holiday_year;
		this.locdate = locdate;
		this.date_name = date_name;
		this.is_holiday = is_holiday;
		this.created_at = created_at;
	}
	public Long getHoliday_seq() {
		return holiday_seq;
	}
	public void setHoliday_seq(Long holiday_seq) {
		this.holiday_seq = holiday_seq;
	}
	public Integer getHoliday_year() {
		return holiday_year;
	}
	public void setHoliday_year(Integer holiday_year) {
		this.holiday_year = holiday_year;
	}
	public String getLocdate() {
		return locdate;
	}
	public void setLocdate(String locdate) {
		this.locdate = locdate;
	}
	public String getDate_name() {
		return date_name;
	}
	public void setDate_name(String date_name) {
		this.date_name = date_name;
	}
	public String getIs_holiday() {
		return is_holiday;
	}
	public void setIs_holiday(String is_holiday) {
		this.is_holiday = is_holiday;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	} 
    
    
}
