package com.study.app.domains.defaultApprovalLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultApprovalLineDAO {
	
	@Autowired
	private SqlSessionTemplate myBatis;
	
	public List<DefaultApprovalLineDTO> getApprovalLines(String doc_type) {
		return myBatis.selectList("DefaultLine.getApprovalLines", doc_type);
	}
	
	public void deleteOriDefaultLine(Map<String, Object> params) {
		myBatis.delete("DefaultLine.deleteDefaultLine", params);
	}
	
	public void insertApprovalLine(DefaultApprovalLineDTO line) {
		myBatis.insert("DefaultLine.insertApprovalLine", line);
	}
	
	public void deleteApprovalLine(String doc_type, Long drafter_rank_seq) {
		Map<String, Object> params = new HashMap<>();
		params.put("doc_type", doc_type);
		params.put("drafter_rank_seq", drafter_rank_seq);
		myBatis.delete("DefaultLine.deleteDefaultLine", params);
	}
	
	public List<DefaultApprovalLineDTO> getDefaultApprovalLine(Map<String, Object> lineparams) {
		return myBatis.selectList("DefaultLine.getDefaultApprovalLine", lineparams);
	}
}
