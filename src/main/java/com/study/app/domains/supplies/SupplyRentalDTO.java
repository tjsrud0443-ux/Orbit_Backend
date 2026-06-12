package com.study.app.domains.supplies;

import java.time.LocalDate;

public class SupplyRentalDTO {
	private Long rental_seq;
	private String supply_name;
	private String category;
	private String supply_code;
	private String dept_name;
	private String name;
	private LocalDate rental_date;
	private LocalDate return_date;
	 // INSERT용 추가
    private Long supply_seq;
    private String users_id;
    private int dept_seq;
    private int ea;
    
	public SupplyRentalDTO() {}

	public SupplyRentalDTO(Long rental_seq, String supply_name, String category, String supply_code, String dept_name,
			String name, LocalDate rental_date, LocalDate return_date, Long supply_seq, String users_id, int dept_seq,
			int ea) {
		super();
		this.rental_seq = rental_seq;
		this.supply_name = supply_name;
		this.category = category;
		this.supply_code = supply_code;
		this.dept_name = dept_name;
		this.name = name;
		this.rental_date = rental_date;
		this.return_date = return_date;
		this.supply_seq = supply_seq;
		this.users_id = users_id;
		this.dept_seq = dept_seq;
		this.ea = ea;
	}

	// INSERT용 생성자 추가
	public SupplyRentalDTO(Long supply_seq, String users_id, int dept_seq, int ea, LocalDate rental_date) {
	    this.supply_seq = supply_seq;
	    this.users_id = users_id;
	    this.dept_seq = dept_seq;
	    this.ea = ea;
	    this.rental_date = rental_date;
	}
	
	public Long getRental_seq() {
		return rental_seq;
	}

	public void setRental_seq(Long rental_seq) {
		this.rental_seq = rental_seq;
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

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getRental_date() {
		return rental_date;
	}

	public void setRental_date(LocalDate rental_date) {
		this.rental_date = rental_date;
	}

	public LocalDate getReturn_date() {
		return return_date;
	}

	public void setReturn_date(LocalDate return_date) {
		this.return_date = return_date;
	}

	public Long getSupply_seq() {
		return supply_seq;
	}

	public void setSupply_seq(Long supply_seq) {
		this.supply_seq = supply_seq;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public int getDept_seq() {
		return dept_seq;
	}

	public void setDept_seq(int dept_seq) {
		this.dept_seq = dept_seq;
	}

	public int getEa() {
		return ea;
	}

	public void setEa(int ea) {
		this.ea = ea;
	}

	
}
