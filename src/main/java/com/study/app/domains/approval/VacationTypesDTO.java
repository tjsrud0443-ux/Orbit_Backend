package com.study.app.domains.approval;

public class VacationTypesDTO {
	private Long v_types_seq;
	private String vac_type;
	private String is_deduction;
	private Double deduct_days;
	
	public VacationTypesDTO() {}
	public VacationTypesDTO(Long v_types_seq, String vac_type, String is_deduction, Double deduct_days) {
		super();
		this.v_types_seq = v_types_seq;
		this.vac_type = vac_type;
		this.is_deduction = is_deduction;
		this.deduct_days = deduct_days;
	}
	public Long getV_types_seq() {
		return v_types_seq;
	}
	public void setV_types_seq(Long v_types_seq) {
		this.v_types_seq = v_types_seq;
	}
	public String getVac_type() {
		return vac_type;
	}
	public void setVac_type(String vac_type) {
		this.vac_type = vac_type;
	}
	public String getIs_deduction() {
		return is_deduction;
	}
	public void setIs_deduction(String is_deduction) {
		this.is_deduction = is_deduction;
	}
	public Double getDeduct_days() {
		return deduct_days;
	}
	public void setDeduct_days(Double deduct_days) {
		this.deduct_days = deduct_days;
	}
}
