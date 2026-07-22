package com.study.app.domains.certType;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CertIssueRequestDAO {
	
	@Autowired
	private SqlSessionTemplate batis;
	
	public void insertCertRequest(Map<String, Object> params) {
		batis.insert("CertIssueRequest.insertCertRequest", params);
	}
	
	public int countActiveCertRequest(Map<String, Object> params) {
		return batis.selectOne("CertIssueRequest.countActiveCertRequest", params);
	}
	
	public List<CertIssueRequestDTO> getAdminCertRequestList() {
		return batis.selectList("CertIssueRequest.getAdminCertRequestList");
	}
	
	public int approveCertRequest(Map<String, Object> params) {
		return batis.update("CertIssueRequest.approveCertRequest", params);
	}
	
	public int rejectCertRequest(Map<String, Object> params) {
		return batis.update("CertIssueRequest.rejectCertRequest", params);
	}
	
	public int cancelCertRequest(Map<String, Object> params) {
		return batis.delete("CertIssueRequest.cancelCertRequest", params);
	}
	
	public int increasePrintedCount(Map<String, Object> params) {
		return batis.update("CertIssueRequest.increasePrintedCount", params);
	}
	
	public CertIssueRequestDTO getPrintableCertRequest(Map<String, Object> params) {
		return batis.selectOne("CertIssueRequest.getPrintableCertRequest", params);
	}
}
