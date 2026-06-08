package com.study.app.domains.aiChat;

public class EmbedChunkDTO {

	private Long chunk_seq;
    private Long rag_doc_seq;
    private String file_name;
    private String chunk_text;
    
	public EmbedChunkDTO() {}
	
	public EmbedChunkDTO(Long chunk_seq, Long rag_doc_seq, String file_name, String chunk_text) {
		super();
		this.chunk_seq = chunk_seq;
		this.rag_doc_seq = rag_doc_seq;
		this.file_name = file_name;
		this.chunk_text = chunk_text;
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
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getChunk_text() {
		return chunk_text;
	}
	public void setChunk_text(String chunk_text) {
		this.chunk_text = chunk_text;
	}
}
