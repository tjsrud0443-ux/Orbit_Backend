package com.study.app.domains.supplies;

import java.time.LocalDateTime;

public class SupplyDTO {
    private Long   supply_seq;
    private String supply_name;
    private String category;
    private String supply_code;
    private Long   total_qty;
    private Long   stock_qty;
    private Long   min_stock_qty;
    private String status;
    private LocalDateTime created_at;
    
    public SupplyDTO() {}
	public SupplyDTO(Long supply_seq, String supply_name, String category, String supply_code, Long total_qty,
			Long stock_qty, Long min_stock_qty, String status, LocalDateTime created_at) {
		super();
		this.supply_seq = supply_seq;
		this.supply_name = supply_name;
		this.category = category;
		this.supply_code = supply_code;
		this.total_qty = total_qty;
		this.stock_qty = stock_qty;
		this.min_stock_qty = min_stock_qty;
		this.status = status;
		this.created_at = created_at;
	}
	public Long getSupply_seq() {
		return supply_seq;
	}
	public void setSupply_seq(Long supply_seq) {
		this.supply_seq = supply_seq;
	}
	public String getSupply_name() {
		return supply_name;
	}
	public void setSupply_name(String supply_name) {
		this.supply_name = supply_name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSupply_code() {
		return supply_code;
	}
	public void setSupply_code(String supply_code) {
		this.supply_code = supply_code;
	}
	public Long getTotal_qty() {
		return total_qty;
	}
	public void setTotal_qty(Long total_qty) {
		this.total_qty = total_qty;
	}
	public Long getStock_qty() {
		return stock_qty;
	}
	public void setStock_qty(Long stock_qty) {
		this.stock_qty = stock_qty;
	}
	public Long getMin_stock_qty() {
		return min_stock_qty;
	}
	public void setMin_stock_qty(Long min_stock_qty) {
		this.min_stock_qty = min_stock_qty;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
    
    
}
