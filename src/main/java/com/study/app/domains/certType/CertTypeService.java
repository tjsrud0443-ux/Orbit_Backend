package com.study.app.domains.certType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CertTypeService {
	
	@Autowired
	private CertTypeDAO dao ;
	@Autowired
	private CertIssueRequestDAO certReqDao;
	
	public List<CertTypeDTO> getCertType(String loginId){
		return dao.getCertType(loginId);
	}
	
	@Transactional
	public void insertCertRequest(CertIssueRequestDTO dto, String loginId) {
		Map<String, Object> params = new HashMap<>();
		params.put("dto", dto);
		params.put("loginId", loginId);
		
		int activeCount = certReqDao.countActiveCertRequest(params);
		
		if(activeCount > 0) {
			throw new IllegalStateException("현재 처리 중이거나 출력 가능한 신청이 존재합니다.");
		}
		
		certReqDao.insertCertRequest(params);
	}
	
	public List<CertIssueRequestDTO> getAdminCertRequestList() {
		return certReqDao.getAdminCertRequestList();
	}
	
	@Transactional
	public void approveCertRequest(Long cert_request_seq, String loginId) { 
		Map<String, Object> params = new HashMap<>();
		params.put("cert_request_seq", cert_request_seq);
		params.put("loginId", loginId);
		
		int result = certReqDao.approveCertRequest(params);
		
		if(result == 0) {
			throw new IllegalStateException("승인할 수 없는 신청이거나 이미 처리된 신청입니다.");
		}
	}
	
	@Transactional
	public void rejectCertRequest(Long cert_request_seq, String reject_reason, String loginId) {
		Map<String, Object> params = new HashMap<>();
		params.put("cert_request_seq", cert_request_seq);
		params.put("reject_reason", reject_reason);
		params.put("loginId", loginId);
		
		int result = certReqDao.rejectCertRequest(params);
		
		if(result == 0) {
			throw new IllegalStateException("반려할 수 없는 신청이거나 이미 처리된 신청입니다.");
		}
	}
	
	@Transactional
	public void cancelCertRequest(Long cert_request_seq, String loginId) {
		Map<String, Object> params = new HashMap<>();
		params.put("cert_request_seq", cert_request_seq);
		params.put("loginId", loginId);
		
		int result = certReqDao.cancelCertRequest(params);
		
		if(result == 0) {
			throw new IllegalStateException("취소할 수 없는 신청이거나 이미 처리된 신청입니다.");
		}
	}
}
