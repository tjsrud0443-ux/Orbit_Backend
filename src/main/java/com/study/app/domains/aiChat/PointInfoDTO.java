package com.study.app.domains.aiChat;

public class PointInfoDTO {
	private Long chunk_seq;
    private String point_id;
    
	public PointInfoDTO() {}
	
	public PointInfoDTO(Long chunk_seq, String point_id) {
		super();
		this.chunk_seq = chunk_seq;
		this.point_id = point_id;
	}
	
	public Long getChunk_seq() {
		return chunk_seq;
	}
	public void setChunk_seq(Long chunk_seq) {
		this.chunk_seq = chunk_seq;
	}
	public String getPoint_id() {
		return point_id;
	}
	public void setPoint_id(String point_id) {
		this.point_id = point_id;
	}
}
