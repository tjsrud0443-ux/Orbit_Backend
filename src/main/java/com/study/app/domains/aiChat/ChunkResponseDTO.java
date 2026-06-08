package com.study.app.domains.aiChat;

import java.util.List;

public class ChunkResponseDTO {
	
	private String raw_text;
	private List<ChunkItemDTO> chunks;
	
	public ChunkResponseDTO() {}
	
	public ChunkResponseDTO(String raw_text, List<ChunkItemDTO> chunks) {
		super();
		this.raw_text = raw_text;
		this.chunks = chunks;
	}
	
	public String getRaw_text() {
		return raw_text;
	}
	public void setRaw_text(String raw_text) {
		this.raw_text = raw_text;
	}
	public List<ChunkItemDTO> getChunks() {
		return chunks;
	}
	public void setChunks(List<ChunkItemDTO> chunks) {
		this.chunks = chunks;
	}
}
