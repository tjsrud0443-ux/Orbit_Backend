package com.study.app.domains.aiChat;

public class RagChunksDTO {
	
	private Long chunk_seq;
	private Long rag_doc_seq;
	private Long chunk_index;
	private String chunk_text;
	private String qdrant_point_id;
	private String embed_status;
	private String embed_at;
	private String created_at;
	
	public RagChunksDTO() {}
	
	public RagChunksDTO(Long chunk_seq, Long rag_doc_seq, Long chunk_index, String chunk_text, String qdrant_point_id,
			String embed_status, String embed_at, String created_at) {
		super();
		this.chunk_seq = chunk_seq;
		this.rag_doc_seq = rag_doc_seq;
		this.chunk_index = chunk_index;
		this.chunk_text = chunk_text;
		this.qdrant_point_id = qdrant_point_id;
		this.embed_status = embed_status;
		this.embed_at = embed_at;
		this.created_at = created_at;
	}
	
	public Long getChunk_seq() {
		return chunk_seq;
	}
	public void setChunk_seq(Long chunk_seq) {
		this.chunk_seq = chunk_seq;
	}
	public Long getRag_doc_seq() {
		return rag_doc_seq;
	}
	public void setRag_doc_seq(Long rag_doc_seq) {
		this.rag_doc_seq = rag_doc_seq;
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
	public String getQdrant_point_id() {
		return qdrant_point_id;
	}
	public void setQdrant_point_id(String qdrant_point_id) {
		this.qdrant_point_id = qdrant_point_id;
	}
	public String getEmbed_status() {
		return embed_status;
	}
	public void setEmbed_status(String embed_status) {
		this.embed_status = embed_status;
	}
	public String getEmbed_at() {
		return embed_at;
	}
	public void setEmbed_at(String embed_at) {
		this.embed_at = embed_at;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}