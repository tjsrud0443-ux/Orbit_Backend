package com.study.app.domains.documents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentsDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<DocumentsDTO> getAllDocs(){
		return mybatis.selectList("Documents.getAllDocs");
	}
	
	public void addDoc(DocumentsDTO dto) {
		mybatis.insert("Documents.addDoc", dto);
	}
	
	public void insertDocFile(DocumentsFilesDTO dto) {
		mybatis.insert("Documents.insertDocFile", dto);
	}
	
	public void updateDocumentTitle(Long document_seq, String title) {
		Map<String, Object> params = new HashMap<>();
		params.put("document_seq", document_seq);
		params.put("title", title);
		mybatis.update("Documents.updateDocumentTitle", params);
	}
	
	public DocumentsFilesDTO findFileByDocSeq(Long document_seq) {
		return mybatis.selectOne("Documents.findFileByDocSeq", document_seq);
	}
	
	public void deleteDocFile(Long file_seq) {
		mybatis.delete("Documents.deleteDocFile", file_seq);
	}
	
	public void deleteFavorDocs(Long document_seq) {
		mybatis.delete("Documents.deleteFavorDocs", document_seq);
	}
	
	public void deleteDocFileByDocSeq(Long document_seq) {
		mybatis.delete("Documents.deleteDocFileByDocSeq", document_seq);
	}
	
	public void deleteDocument(Long document_seq) {
		mybatis.delete("Documents.deleteDocument", document_seq);
	}
	
	public List<DocumentBookmarksDTO> getFavorites(String users_id){
		return mybatis.selectList("Documents.getFavorites", users_id);
	}
	
	public void addFavorite(Long document_seq, String users_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("document_seq", document_seq);
		params.put("users_id", users_id);
		mybatis.insert("Documents.addFavorite", params);
	}
	
	public void removeFavorite(Long document_seq, String users_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("document_seq", document_seq);
		params.put("users_id", users_id);
		mybatis.delete("Documents.removeFavorite", params);
	}
}
