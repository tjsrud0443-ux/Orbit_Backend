package com.study.app.domains.certType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertTypeService {
	
	@Autowired
	private CertTypeDAO dao ;
	@Autowired
	private CertIssueRequestDAO certReqDao;
	
	public List<CertTypeDTO> getCertType(){
		return dao.getCertType();
	}
	
	public void insertCertRequest(CertIssueRequestDTO dto, String loginId) {
		Map<String, Object> params = new HashMap<>();
		params.put("dto", dto);
		params.put("loginId", loginId);
		certReqDao.insertCertRequest(params);
	}
	
	public List<CertIssueRequestDTO> getAdminCertRequestList() {
		return certReqDao.getAdminCertRequestList();
	}
}
