package com.study.app.domains.aiChat;

import java.util.List;

public class DeletePointIdsRequestDTO {
	
	private List<String> point_ids;

	public DeletePointIdsRequestDTO() {}

	public DeletePointIdsRequestDTO(List<String> point_ids) {
		super();
		this.point_ids = point_ids;
	}

	public List<String> getPoint_ids() {
		return point_ids;
	}

	public void setPoint_ids(List<String> point_ids) {
		this.point_ids = point_ids;
	}
}
