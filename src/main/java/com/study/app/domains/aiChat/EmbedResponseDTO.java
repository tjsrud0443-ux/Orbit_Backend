package com.study.app.domains.aiChat;

import java.util.List;

public class EmbedResponseDTO {

	private boolean success;
	private List<PointInfoDTO> points;
	
	public EmbedResponseDTO() {}
	
	public EmbedResponseDTO(boolean success, List<PointInfoDTO> points) {
		super();
		this.success = success;
		this.points = points;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<PointInfoDTO> getPoints() {
		return points;
	}
	public void setPoints(List<PointInfoDTO> points) {
		this.points = points;
	}
}
