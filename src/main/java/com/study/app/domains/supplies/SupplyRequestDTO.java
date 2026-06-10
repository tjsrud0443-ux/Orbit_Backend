package com.study.app.domains.supplies;

import java.time.LocalDate;
import java.util.List;

public class SupplyRequestDTO {
	private Long req_seq;
    private String users_id;
    private LocalDate req_date;
    private String reason;
    private String status;
    private List<SupplyRequestItemsDTO> items;
    
    public SupplyRequestDTO() {}
	public SupplyRequestDTO(Long req_seq, String users_id, LocalDate req_date, String reason, String status,
			List<SupplyRequestItemsDTO> items) {
		super();
		this.req_seq = req_seq;
		this.users_id = users_id;
		this.req_date = req_date;
		this.reason = reason;
		this.status = status;
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
	public List<SupplyRequestItemsDTO> getItems() {
		return items;
	}
	public void setItems(List<SupplyRequestItemsDTO> items) {
		this.items = items;
	}
    
}
