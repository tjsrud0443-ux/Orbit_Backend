package com.study.app.domains.aiChat;

public class SearchResultDTO {

	private Long chunk_seq;
    private Long rag_doc_seq;
    private String file_name;
    private String text;
    private Double score;
	
    public SearchResultDTO() {}
	
	public SearchResultDTO(Long chunk_seq, Long rag_doc_seq, String file_name, String text, Double score) {
		super();
		this.chunk_seq = chunk_seq;
		this.rag_doc_seq = rag_doc_seq;
		this.file_name = file_name;
		this.text = text;
		this.score = score;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
}