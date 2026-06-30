package com.study.app.domains.aiChat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RagDAO {
	
	@Autowired
	private SqlSessionTemplate batis;
	
	public void insertRagDocuments(RagDocumentsDTO dto) {
		batis.insert("Rag.insertRagDocuments", dto);
	}
	
	public void insertRagChunks(RagChunksDTO dto) {
		batis.insert("Rag.insertRagChunks", dto);
	}
	
	public List<RagChunksDTO> findAllChunks() {
	    return batis.selectList("Rag.findAllChunks");
	}
	
	public List<RagChunksDTO> findChunksByRagDocSeq(Long rag_doc_seq) {
	    return batis.selectList("Rag.findChunksByRagDocSeq", rag_doc_seq);
	}
	
	public void updateChunkEmbed(Long chunk_seq, String qdrant_point_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("chunk_seq", chunk_seq);
		params.put("qdrant_point_id", qdrant_point_id);
		batis.update("Rag.updateChunkEmbed", params);
	}
	
	public Long findRagDocSeq(String source_type, Long source_seq) {
		Map<String, Object> params = new HashMap<>();
		params.put("source_type", source_type);
		params.put("source_seq", source_seq);
		return batis.selectOne("Rag.findRagDocSeq", params);
	}
	
	public List<String> findPointIdsByRagDocSeq(Long rag_doc_seq) {
		return batis.selectList("Rag.findPointIdsByRagDocSeq", rag_doc_seq);
	}
	
	public void deleteRagChunksByRagDocSeq(Long rag_doc_seq) {
    	batis.delete("Rag.deleteRagChunksByRagDocSeq", rag_doc_seq);
    }
    
    public void deleteRagDocumentsByRagDocSeq(Long rag_doc_seq) {
    	batis.delete("Rag.deleteRagDocumentsByRagDocSeq", rag_doc_seq);
    }
	
	public List<RagDocumentsDTO> sourcesByRagDocSeqs(List<Long> ragDocSeqs) {
		return batis.selectList("Rag.sourcesByRagDocSeqs", ragDocSeqs);
	}
	
	public List<Long> findRagDocSeqsByMeetingSeq(Map<String, Object> params) {
		return batis.selectList("Rag.findRagDocSeqsByMeetingSeq", params);
	}
}
