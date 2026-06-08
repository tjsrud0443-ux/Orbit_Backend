package com.study.app.domains.aiChat;

import java.util.List;

public class EmbedRequestDTO {

	private List<EmbedChunkDTO> chunks;

	public EmbedRequestDTO() {}

	public EmbedRequestDTO(List<EmbedChunkDTO> chunks) {
		super();
		this.chunks = chunks;
	}

	public List<EmbedChunkDTO> getChunks() {
		return chunks;
	}

	public void setChunks(List<EmbedChunkDTO> chunks) {
		this.chunks = chunks;
	}
}
