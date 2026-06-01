package com.study.app.domains.aiChat;


public class AiUnansweredQuestionsDTO {
	
	private Long question_seq;
	private String users_id;
	private String category;
	private String question;
	private Long dept_seq;
	private String handle_answer;
	private String users_handle_id;
	private String status;
	private String answer_at;
	private String created_at;
	
	public AiUnansweredQuestionsDTO() {}
	
	public AiUnansweredQuestionsDTO(Long question_seq, String users_id, String category, String question, Long dept_seq,
			String handle_answer, String users_handle_id, String status, String answer_at, String created_at) {
		super();
		this.question_seq = question_seq;
		this.users_id = users_id;
		this.category = category;
		this.question = question;
		this.dept_seq = dept_seq;
		this.handle_answer = handle_answer;
		this.users_handle_id = users_handle_id;
		this.status = status;
		this.answer_at = answer_at;
		this.created_at = created_at;
	}
	
	public Long getQuestion_seq() {
		return question_seq;
	}
	public void setQuestion_seq(Long question_seq) {
		this.question_seq = question_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Long getDept_seq() {
		return dept_seq;
	}
	public void setDept_seq(Long dept_seq) {
		this.dept_seq = dept_seq;
	}
	public String getHandle_answer() {
		return handle_answer;
	}
	public void setHandle_answer(String handle_answer) {
		this.handle_answer = handle_answer;
	}
	public String getUsers_handle_id() {
		return users_handle_id;
	}
	public void setUsers_handle_id(String users_handle_id) {
		this.users_handle_id = users_handle_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAnswer_at() {
		return answer_at;
	}
	public void setAnswer_at(String answer_at) {
		this.answer_at = answer_at;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}