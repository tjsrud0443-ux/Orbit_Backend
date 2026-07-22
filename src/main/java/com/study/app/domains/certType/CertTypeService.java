package com.study.app.domains.certType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

@Service
public class CertTypeService {

	@Autowired
	private CertTypeDAO dao ;
	@Autowired
	private CertIssueRequestDAO certReqDao;
	@Autowired
	private CertIssueHistoryDAO certHisDao;

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

	@Transactional
	public Map<String, Object> createCertIssue(Long cert_request_seq, String loginId) {
		Map<String, Object> params = new HashMap<>();
		params.put("cert_request_seq", cert_request_seq);
		params.put("loginId", loginId);

		CertIssueRequestDTO request = certReqDao.getPrintableCertRequest(params);
		if (request == null) {
			throw new IllegalStateException("발급 가능한 증명서를 찾을 수 없습니다.");
		}

		Long printedCount =
				request.getPrinted_count() == null
				? 0L : request.getPrinted_count();

		Long maxPrintCount =
				request.getApplied_max_print() == null
				? 0L : request.getApplied_max_print();

		if (printedCount >= maxPrintCount) {
			throw new IllegalStateException("출력 가능 횟수를 모두 사용했습니다.");
		}

		CertIssueHistoryDTO pendingIssue = certHisDao.getPendingIssueHistory(params);

		Long issueSeq;
		Long issueNo;
		String issueDateCode;

		if(pendingIssue != null) {
			issueSeq = pendingIssue.getIssue_seq();
			issueNo = pendingIssue.getIssue_no();
			issueDateCode = pendingIssue.getIssue_date_code();
		}else {
			issueNo = certHisDao.getNextIssueNo();

			if (issueNo == null) {
				throw new IllegalStateException("발급번호 생성에 실패했습니다.");
			}
			
			Map<String, Object> historyParams = new HashMap<>();

			historyParams.put("cert_request_seq", cert_request_seq);
			historyParams.put("issue_no", issueNo);

			int historyResult = certHisDao.insertCertIssueHistory(historyParams);
			if (historyResult == 0) {
				throw new IllegalStateException("증명서 발급 이력 등록에 실패했습니다.");
			}

			Object issueSeqValue = historyParams.get("issue_seq");
			if (issueSeqValue == null) {
				throw new IllegalStateException("발급 이력 번호 생성에 실패했습니다.");
			}

			issueSeq = ((Number) issueSeqValue).longValue();

			issueDateCode =
					LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		}

		String issueNumber =
				issueDateCode + "-" + String.format("%03d",issueNo);

		Map<String, Object> result = new HashMap<>();

		result.put("issue_seq", issueSeq);
		result.put("issue_date_code", issueDateCode);
		result.put("issue_no", issueNo);
		result.put("issue_number", issueNumber);
		result.put("printed_count", printedCount);
		result.put("remaining_count", Math.max(maxPrintCount - printedCount, 0L));

		return result;
	}

	@Transactional
	public Map<String, Object> printCertificate(Long cert_request_seq, Long issue_seq, String loginId) {
		Map<String, Object> params = new HashMap<>();
		params.put("cert_request_seq", cert_request_seq);
		params.put("issue_seq", issue_seq);
		params.put("loginId", loginId);

		int historyResult = certHisDao.markPrinted(params);
		if(historyResult == 0) {
			throw new IllegalStateException("출력할 수 없는 발급번호이거나 이미 출력 처리된 증명서입니다.");
		}

		int countResult = certReqDao.increasePrintedCount(params);
		if(countResult == 0) {
			throw new IllegalStateException("출력 기한이 만료되었거나 출력 가능 횟수를 모두 사용했습니다.");
		}

		Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		result.put("message", "출력 처리되었습니다.");

		return result;
	}

	public List<CertTypeDTO> getAdminCertTypeList() {
		return dao.getAdminCertTypeList();
	}

	public void updateCertTypeHidden(CertTypeDTO dto) {
		dao.updateCertTypeHidden(dto);
	}

	public void updateCertType(CertTypeDTO dto) {
		dao.updateCertType(dto);
	}

	public List<CertIssueHistoryDTO> getCertIssueHistoryList() {
		return certHisDao.getCertIssueHistoryList();
	}
}
