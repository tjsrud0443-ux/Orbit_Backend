package com.study.app.domains.rank;

public class RankDTO {
	private Long rank_seq;
	private String rank_name;
	private Long rank_order;
	
	public RankDTO() {}
	
	public RankDTO(Long rank_seq, String rank_name, Long rank_order) {
		super();
		this.rank_seq = rank_seq;
		this.rank_name = rank_name;
		this.rank_order = rank_order;
	}
	
	public Long getRank_seq() {
		return rank_seq;
	}
	public void setRank_seq(Long rank_seq) {
		this.rank_seq = rank_seq;
	}
	public String getRank_name() {
		return rank_name;
	}
	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}
	public Long getRank_order() {
		return rank_order;
	}
	public void setRank_order(Long rank_order) {
		this.rank_order = rank_order;
	}
}
