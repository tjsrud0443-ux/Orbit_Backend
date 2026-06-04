package com.study.app.domains.documents;

import java.util.List;

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
}
