package com.study.app.domains.aiChat;

import java.util.List;

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
	
}
