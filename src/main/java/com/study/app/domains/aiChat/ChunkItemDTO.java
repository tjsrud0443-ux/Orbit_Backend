package com.study.app.domains.aiChat;

public class ChunkItemDTO {
	
	private Long chunk_index;
    private String chunk_text;
    
	public ChunkItemDTO() {}
	
	public ChunkItemDTO(Long chunk_index, String chunk_text) {
		super();
		this.chunk_index = chunk_index;
		this.chunk_text = chunk_text;
	}
	
	public Long getChunk_index() {
		return chunk_index;
	}
	public void setChunk_index(Long chunk_index) {
		this.chunk_index = chunk_index;
	}
	public String getChunk_text() {
		return chunk_text;
	}
	public void setChunk_text(String chunk_text) {
		this.chunk_text = chunk_text;
	}
}
