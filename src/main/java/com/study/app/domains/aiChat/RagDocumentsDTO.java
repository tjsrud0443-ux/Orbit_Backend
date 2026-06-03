package com.study.app.domains.aiChat;

public class RagDocumentsDTO {
	
	private Long rag_doc_seq;
	private String source_type;
	private Long source_seq;
	private Long file_seq;
	private String file_name;
	private String file_ext;
	private String raw_text;
	private String extract_status;
	private String fail_reason;
	private String extracted_at;
	private String created_at;
	
	public RagDocumentsDTO() {}
	
	public RagDocumentsDTO(Long rag_doc_seq, String source_type, Long source_seq, Long file_seq, String file_name,
			String file_ext, String raw_text, String extract_status, String fail_reason, String extracted_at,
			String created_at) {
		super();
		this.rag_doc_seq = rag_doc_seq;
		this.source_type = source_type;
		this.source_seq = source_seq;
		this.file_seq = file_seq;
		this.file_name = file_name;
		this.file_ext = file_ext;
		this.raw_text = raw_text;
		this.extract_status = extract_status;
		this.fail_reason = fail_reason;
		this.extracted_at = extracted_at;
		this.created_at = created_at;
	}
	
	public Long getRag_doc_seq() {
		return rag_doc_seq;
	}
	public void setRag_doc_seq(Long rag_doc_seq) {
		this.rag_doc_seq = rag_doc_seq;
	}
	public String getSource_type() {
		return source_type;
	}
	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}
	public Long getSource_seq() {
		return source_seq;
	}
	public void setSource_seq(Long source_seq) {
		this.source_seq = source_seq;
	}
	public Long getFile_seq() {
		return file_seq;
	}
	public void setFile_seq(Long file_seq) {
		this.file_seq = file_seq;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_ext() {
		return file_ext;
	}
	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}
	public String getRaw_text() {
		return raw_text;
	}
	public void setRaw_text(String raw_text) {
		this.raw_text = raw_text;
	}
	public String getExtract_status() {
		return extract_status;
	}
	public void setExtract_status(String extract_status) {
		this.extract_status = extract_status;
	}
	public String getFail_reason() {
		return fail_reason;
	}
	public void setFail_reason(String fail_reason) {
		this.fail_reason = fail_reason;
	}
	public String getExtracted_at() {
		return extracted_at;
	}
	public void setExtracted_at(String extracted_at) {
		this.extracted_at = extracted_at;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}