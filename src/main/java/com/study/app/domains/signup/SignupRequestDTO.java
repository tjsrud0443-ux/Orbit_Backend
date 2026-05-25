package com.study.app.domains.signup;

public class SignupRequestDTO {
	private Long signup_seq;
    private Long dept_seq;
    private Long rank_seq;
    private String hire_date;
    
    public SignupRequestDTO() {}
	public SignupRequestDTO(Long signup_seq, Long dept_seq, Long rank_seq, String hire_date) {
		super();
		this.signup_seq = signup_seq;
		this.dept_seq = dept_seq;
		this.rank_seq = rank_seq;
		this.hire_date = hire_date;
	}
	public Long getSignup_seq() {
		return signup_seq;
	}
	public void setSignup_seq(Long signup_seq) {
		this.signup_seq = signup_seq;
	}
	public Long getDept_seq() {
		return dept_seq;
	}
	public void setDept_seq(Long dept_seq) {
		this.dept_seq = dept_seq;
	}
	public Long getRank_seq() {
		return rank_seq;
	}
	public void setRank_seq(Long rank_seq) {
		this.rank_seq = rank_seq;
	}
	public String getHire_date() {
		return hire_date;
	}
	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}
}
