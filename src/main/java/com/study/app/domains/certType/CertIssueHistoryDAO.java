package com.study.app.domains.certType;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CertIssueHistoryDAO {
	
	@Autowired
	private SqlSessionTemplate batis;
	
	public Long getNextIssueNo() {
		return batis.selectOne("CertIssueHistory.getNextIssueNo");
	}
	
	public int insertCertIssueHistory(Map<String, Object> params) {
		return batis.insert("CertIssueHistory.insertCertIssueHistory",params);
	}
	
	public int markPrinted(Map<String, Object> params) {
		return batis.update("CertIssueHistory.markPrinted", params);
	}
	
	public List<CertIssueHistoryDTO> getCertIssueHistoryList() {
		return batis.selectList("CertIssueHistory.getCertIssueHistoryList");
	}
	
	public CertIssueHistoryDTO getPendingIssueHistory(Map<String, Object> params) {
		return batis.selectOne("CertIssueHistory.getPendingIssueHistory", params);
	}
}
