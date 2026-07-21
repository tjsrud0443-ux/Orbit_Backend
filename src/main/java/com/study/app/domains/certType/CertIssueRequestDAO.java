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
	
	public List<CertIssueRequestDTO> getAdminCertRequestList() {
		return batis.selectList("CertIssueRequest.getAdminCertRequestList");
	}
	
}
