package com.study.app.domains.supplies;

public class SupplyRequestItemsDTO {
	private Long item_seq;
    private Long req_seq;
    private Long supply_seq;
    private String supply_name;
    private int ea;
    private String use_type;
    //db에 없음
    private Long stock_qty;      
    private Long min_stock_qty;  
    
    public SupplyRequestItemsDTO() {}

	public SupplyRequestItemsDTO(Long item_seq, Long req_seq, Long supply_seq, String supply_name, int ea,
			String use_type, Long stock_qty, Long min_stock_qty) {
		super();
		this.item_seq = item_seq;
		this.req_seq = req_seq;
		this.supply_seq = supply_seq;
		this.supply_name = supply_name;
		this.ea = ea;
		this.use_type = use_type;
		this.stock_qty = stock_qty;
		this.min_stock_qty = min_stock_qty;
	}

	public Long getItem_seq() {
		return item_seq;
	}

	public void setItem_seq(Long item_seq) {
		this.item_seq = item_seq;
	}

	public Long getReq_seq() {
		return req_seq;
	}

	public void setReq_seq(Long req_seq) {
		this.req_seq = req_seq;
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

	public int getEa() {
		return ea;
	}

	public void setEa(int ea) {
		this.ea = ea;
	}

	public String getUse_type() {
		return use_type;
	}

	public void setUse_type(String use_type) {
		this.use_type = use_type;
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

	
}
