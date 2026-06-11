package com.study.app.domains.supplies;

import java.time.LocalDate;
import java.util.List;

public class SupplyRequestDTO {
	private Long req_seq;
    private String users_id;
    private LocalDate req_date;//신청일-> 수령 희망 날짜로 변경
    private String reason;
    private String status;
    //db에 없다
    private String user_name;
    private List<SupplyRequestItemsDTO> items;
    
    public SupplyRequestDTO() {}

	public SupplyRequestDTO(Long req_seq, String users_id, LocalDate req_date, String reason, String status,
			String user_name, List<SupplyRequestItemsDTO> items) {
		super();
		this.req_seq = req_seq;
		this.users_id = users_id;
		this.req_date = req_date;
		this.reason = reason;
		this.status = status;
		this.user_name = user_name;
		this.items = items;
	}


	public Long getReq_seq() {
		return req_seq;
	}

	public void setReq_seq(Long req_seq) {
		this.req_seq = req_seq;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public LocalDate getReq_date() {
		return req_date;
	}

	public void setReq_date(LocalDate req_date) {
		this.req_date = req_date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public List<SupplyRequestItemsDTO> getItems() {
		return items;
	}

	public void setItems(List<SupplyRequestItemsDTO> items) {
		this.items = items;
	}
	
}
