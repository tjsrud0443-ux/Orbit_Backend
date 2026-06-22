package com.study.app.domains.aiChat;

import java.util.List;

public class AiMessagesDTO {

	private Long msg_seq;
	private Long chat_seq;
	private String role;
	private String content;
	private String ref_chunk_ids;
	private String created_at;
	private String status;
	private String ref_rag_doc_ids;
	private List<RagDocumentsDTO> resultSources;
	
	public AiMessagesDTO() {}
	
	public AiMessagesDTO(Long msg_seq, Long chat_seq, String role, String content, String ref_chunk_ids,
			String created_at, String status, String ref_rag_doc_ids) {
		super();
		this.msg_seq = msg_seq;
		this.chat_seq = chat_seq;
		this.role = role;
		this.content = content;
		this.ref_chunk_ids = ref_chunk_ids;
		this.created_at = created_at;
		this.status = status;
		this.ref_rag_doc_ids = ref_rag_doc_ids;
	}
	
	public Long getMsg_seq() {
		return msg_seq;
	}
	public void setMsg_seq(Long msg_seq) {
		this.msg_seq = msg_seq;
	}
	public Long getChat_seq() {
		return chat_seq;
	}
	public void setChat_seq(Long chat_seq) {
		this.chat_seq = chat_seq;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRef_chunk_ids() {
		return ref_chunk_ids;
	}
	public void setRef_chunk_ids(String ref_chunk_ids) {
		this.ref_chunk_ids = ref_chunk_ids;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRef_rag_doc_ids() {
		return ref_rag_doc_ids;
	}
	public void setRef_rag_doc_ids(String ref_rag_doc_ids) {
		this.ref_rag_doc_ids = ref_rag_doc_ids;
	}
	public List<RagDocumentsDTO> getResultSources() {
		return resultSources;
	}
	public void setResultSources(List<RagDocumentsDTO> resultSources) {
		this.resultSources = resultSources;
	}
	
}