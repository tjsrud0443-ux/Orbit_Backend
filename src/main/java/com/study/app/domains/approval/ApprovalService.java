package com.study.app.domains.approval;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@Service
public class ApprovalService {

	@Autowired
	private ApprovalDAO dao;
	@Autowired
	private Storage storage;
	@Value("${spring.cloud.gcp.bucket}")
	private String bucketName;

	private void insertCommonApprovalData(DraftDocumentsDTO dto) {
		if(dto.getIs_temp() == 1) {
			LocalDate expireDate = LocalDate.now().plusDays(7);
	        dto.setTemp_expires_at(expireDate.toString());
		}else {
			dto.setTemp_expires_at(null);
		}
		// selectKey(BEFORE)에 의해 실행 후 docSeq 필드에 시퀀스 값이 채워짐
		dao.insertDraftDocument(dto); 
		Long docSeq = dto.getDoc_seq(); // 채워진 시퀀스 꺼내기

		// 결재라인 정보 저장
		if(dto.getApprovers() != null) {
			for (ApprovalLinesDTO app : dto.getApprovers()) {
				app.setDoc_seq(docSeq); // 외래키 주입
				if (app.getStep_order() == 1) {
					app.setStatus("IN_PROGRESS"); 
				} else {
					app.setStatus("WAITING");
				}
				dao.insertApprovalLines(app);
			}
		}

		// 참조자 정보 저장
		if(dto.getReferrers() != null) {
			for (ApprovalCcDTO ref : dto.getReferrers()) {
				ref.setDoc_seq(docSeq);
				dao.insertReferrers(ref);
			}
		}
	}
	// 휴가 신청서
	@Transactional
	public void insertVacation(VacationDTO dto) {
		insertCommonApprovalData(dto);
		dao.insertVacationDetail(dto);
	}
	// 일반 품의서
	@Transactional
	public void insertGeneral(GeneralDTO dto) {
		insertCommonApprovalData(dto);
		dao.insertGeneralDetail(dto);
	}
	// 지출 결의서
	@Transactional
	public void insertPayment(PaymentDTO dto, List<MultipartFile> files) {
		insertCommonApprovalData(dto);
		dao.insertPaymentDetail(dto);

		// 지출 세부 항목 및 개별 파일 업로드
		if(dto.getItems() != null) {
			for(int i = 0; i < dto.getItems().size(); i++) {
				PaymentItemsDTO item = dto.getItems().get(i);

				item.setPay_seq(dto.getPay_seq());
				item.setItem_order((Long.valueOf(i + 1)));

				if(files != null && i < files.size() && !files.get(i).isEmpty()) {
					MultipartFile file = files.get(i);
					try {
						String oriname = file.getOriginalFilename();
						String sysname = UUID.randomUUID().toString() + "_" + oriname;

						// GCS 업로드
						BlobId blobId = BlobId.of(bucketName, sysname);
						BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
								.setContentType(file.getContentType()).build();

						storage.create(blobInfo, file.getBytes());

						// DB에 저장
						item.setOriname(oriname);
						item.setSysname(sysname);

					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException("영수증 파일 업로드 중 오류 발생", e);
					}
				}

				dao.insertPaymentItem(item);
			}
		}
	}
	// 구매 신청서
	@Transactional
	public void insertPurchase(PurchaseDTO dto, List<MultipartFile> files) {
		insertCommonApprovalData(dto);
		dao.insertPurchaseDetail(dto);
		Long purchase_seq = dto.getPurchase_seq();

		if (dto.getItems() != null) {
			for (int i = 0; i < dto.getItems().size(); i++) {
				PurchaseItemsDTO item = dto.getItems().get(i);
				item.setPurchase_seq(purchase_seq);
				item.setItem_order((Long.valueOf(i + 1)));
				dao.insertPurchaseItem(item);
			}
		}
		// 구매 첨부파일 리스트
		if(files != null && !files.isEmpty()) {
			for(MultipartFile file : files) {
				if(!file.isEmpty()) {
					try {
						String oriname = file.getOriginalFilename();
						String sysname = UUID.randomUUID().toString() + "_" + oriname;

						// GCS 업로드
						BlobId blobId = BlobId.of(bucketName, sysname);
						BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
								.setContentType(file.getContentType()).build();

						storage.create(blobInfo, file.getBytes());

						PurchaseAttachmentsDTO attach = new PurchaseAttachmentsDTO();
						attach.setPurchase_seq(purchase_seq);
						attach.setOriname(oriname);
						attach.setSysname(sysname);

						dao.insertPurchaseAttachment(attach);

					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException(file.getOriginalFilename() + " 파일 업로드 중 오류 발생", e);
					}
				}
			}
		}
	}
	
	public Map<String, Object> getApprovalDetail(String doc_type, Long doc_seq) throws Exception {
        Map<String, Object> result = new HashMap<>();

        // 공통 정보 조회 (draft_documents)
        Map<String, Object> common = dao.selectCommonDetail(doc_seq);
        if (common == null) {
            throw new IllegalArgumentException("존재하지 않는 결재 문서입니다.");
        }
        result.put("common", common);

        // 결재자 및 참조자 리스트 조회
        List<Map<String, Object>> approvers = dao.selectApprovers(doc_seq);
        List<Map<String, Object>> referrers = dao.selectReferrers(doc_seq);
        result.put("approvers", approvers);
        result.put("referrers", referrers);

        // 하위 데이터 조회
        Map<String, Object> detail = null;

        switch (doc_type) {
            case "VACATION":
                detail = dao.selectVacationDetail(doc_seq);
                break;
                
            case "GENERAL":
                detail = dao.selectGeneralDetail(doc_seq);
                break;
                
            case "PAYMENT":
                detail = dao.selectPaymentDetail(doc_seq);
                if (detail != null) {
                	Object paySeqObj = detail.get("pay_seq");
                    Long pay_seq = Long.parseLong(String.valueOf(paySeqObj));
                    
                    List<Map<String, Object>> paymentItems = dao.selectPaymentItems(pay_seq);
                    result.put("items", paymentItems);
                }
                break;
                
            case "PURCHASE":
                detail = dao.selectPurchaseDetail(doc_seq);
                if (detail != null) {
                	Object purchaseSeqObj = detail.get("purchase_seq");
                    Long purchase_seq = Long.parseLong(String.valueOf(purchaseSeqObj));
                    
                    List<Map<String, Object>> purchaseItems = dao.selectPurchaseItems(purchase_seq);
                    result.put("items", purchaseItems);
                    
                    List<Map<String, Object>> attachments = dao.selectPurchaseAttachments(purchase_seq);
                    result.put("attachments", attachments);
                }
                break;
                
            default:
                throw new IllegalArgumentException("잘못된 문서 타입입니다: " + doc_type);
        }

        result.put("detail", detail);

        return result;
    }

//	@Transactional
//	public void approveDocument(Long doc_seq, String users_id) {
//	    // 현재 결재자의 라인 정보 조회
//	    Map<String, Object> currentLine = dao.selectMyApprovalLine(doc_seq, users_id);
//	    Long currentStepOrder = Long.parseLong(String.valueOf(currentLine.get("step_order")));
//
//	    // 현재 결재자 status → APPROVED, handle_at → sysdate
//	    dao.updateApprovalLineStatus(doc_seq, users_id, "APPROVED");
//
//	    // 다음 결재자 조회
//	    Map<String, Object> nextLine = dao.selectNextApprovalLine(doc_seq, currentStepOrder);
//
//	    if (nextLine != null) {
//	        // 중간 결재자 → 다음 결재자 IN_PROGRESS, 문서 IN_PROGRESS 유지
//	        String nextUsersId = String.valueOf(nextLine.get("users_id"));
//	        dao.updateApprovalLineStatus(doc_seq, nextUsersId, "IN_PROGRESS");
//	        dao.updateDocumentStatus(doc_seq, "IN_PROGRESS");
//	    } else {
//	        // 마지막 결재자 → 문서 APPROVED
//	        dao.updateDocumentStatus(doc_seq, "APPROVED");
//	    }
//	}



















































































































































	public List<DraftDocumentsDTO> getCcWithLine(String loginId) {
		List<DraftDocumentsDTO> ccList = dao.getCcList(loginId);

		for(DraftDocumentsDTO draftDoc : ccList) {
			List<ApprovalLinesDTO> lines = dao.getLinesBySeq(draftDoc.getDoc_seq());

			draftDoc.setApprovers(lines);			
		}

		return ccList;
	}

	public Map<String, Object> getCcDocumentsByPage(String loginId, String status, Long cpage, 
			String keyword, String docType){
		int start = (int)(cpage * 5 - 4);
		int end = (int)(cpage * 5);
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("loginId", loginId);
		param.put("status", status);
		param.put("keyword", keyword);
		param.put("docType", docType);
		param.put("start", start);
		param.put("end", end);
		
		List<DraftDocumentsDTO> list = dao.getCcPage(param);
		
		for(DraftDocumentsDTO draftDoc : list) {
			List<ApprovalLinesDTO> lines = dao.getLinesBySeq(draftDoc.getDoc_seq());

			draftDoc.setApprovers(lines);			
		}
		
		int count = dao.getCcpageCount(param);
		Map<String, Object> result = new HashMap<>();
		
		result.put("list", list);
		result.put("count", count);
		
		return result;
	}
	
	public List<DraftDocumentsDTO> getMyDocWithLine(String loginId) {
		List<DraftDocumentsDTO> myDocList = dao.getMyDoc(loginId);
		
		for(DraftDocumentsDTO dto : myDocList) {
			List<ApprovalLinesDTO> lines = dao.getLinesBySeq(dto.getDoc_seq());
			dto.setApprovers(lines);
		}
		return myDocList;
	}
	
	public Map<String, Object> getMyDocumentsByPage(String loginId, String status, Long cpage,
			String keyword, String docType) {
		int start = (int)(cpage * 5 - 4);
		int end = (int)(cpage * 5);
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("loginId", loginId);
		param.put("status", status);
		param.put("keyword", keyword);
		param.put("docType", docType);
		param.put("start", start);
		param.put("end", end);
		
		List<DraftDocumentsDTO> list = dao.getMyDocPage(param);
		
		for(DraftDocumentsDTO dto : list) {
			List<ApprovalLinesDTO> lines = dao.getLinesBySeq(dto.getDoc_seq());

			dto.setApprovers(lines);
		}
		
		int count = dao.getMyDocPageCount(param);
		Map<String, Object> result = new HashMap<>();
		
		result.put("list", list);
		result.put("count", count);
		
		return result;
	}
	
	public List<DraftDocumentsDTO> getMydraftDoc(String loginId) {
		List<DraftDocumentsDTO> mydraftDocList = dao.getMydraftDoc(loginId);
		
			for(DraftDocumentsDTO dto : mydraftDocList) {
				List<ApprovalLinesDTO> lines = dao.getLinesBySeq(dto.getDoc_seq());
				dto.setApprovers(lines);
			}
		return mydraftDocList;
	}
	
	public Map<String, Object> getMyDoneDocByPage(String loginId, Long cpage, String keyword, String docType) {
		int start = (int)(cpage * 5 - 4);
		int end = (int)(cpage * 5);
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("loginId", loginId);
		param.put("keyword", keyword);
		param.put("docType", docType);
		param.put("start", start);
		param.put("end", end);
		
		List<DraftDocumentsDTO> list = dao.getMyDoneDocByPage(param);
		
		int count = dao.getMyDoneDocCount(param);
		Map<String, Object> result = new HashMap<>();
		
		result.put("list", list);
		result.put("count", count);
		
		return result;
	}
	
	public List<DraftDocumentsDTO> getTempDoc(String loginId) {
		return dao.getTempDoc(loginId);
	}
	
	public List<DraftDocumentsDTO> tempList() {
		return dao.tempList();
	}
	
	@Transactional
	public void deleteTempDoc(Long doc_seq , String doc_type) {
		switch(doc_type) {
		// 기안 문서 종류 (VACATION(연차) / PAYMENT(지출) / GENERAL(일반) / PURCHASE(구매))
			case "VACATION" :
				deleteVacationDoc(doc_seq);
				break;
		
			case "GENERAL" :
				deleteGeneralDoc(doc_seq);
				break;
				
			case "PAYMENT" :
				deletePaymentDoc(doc_seq);
				break;
				
			case "PURCHASE" :
				deletePurchaseDoc(doc_seq);
				break;	
			
			 default:
	                throw new IllegalArgumentException("잘못된 문서 타입입니다: " + doc_type);
		}
	}
	
	// 공통 삭제 요소 (임시문서)
	private void deleteCommon(Long doc_seq) {
		dao.deleteAppLine(doc_seq);
		dao.deleteAppCc(doc_seq);
		dao.deleteDraftDoc(doc_seq);
	}
	
	// 휴가신청서 (임시문서)
	private void deleteVacationDoc(Long doc_seq) {
		dao.deleteVacDoc(doc_seq);
		deleteCommon(doc_seq);
	}
	
	// 일반품의서 (임시문서)
	private void deleteGeneralDoc(Long doc_seq) {
		dao.deleteGenDoc(doc_seq);
		deleteCommon(doc_seq);
	}
	
	// 지출결의서 (임시문서)
	private void deletePaymentDoc(Long doc_seq) {
		dao.deletePayItem(doc_seq);
		dao.deletePayDoc(doc_seq);
		
		deleteCommon(doc_seq);
	}
	
	// 구매신청서 (임시문서)
	private void deletePurchaseDoc(Long doc_seq) {
		dao.deletePurAttach(doc_seq);
		dao.deletePurItem(doc_seq);
		dao.deletePurDoc(doc_seq);
		deleteCommon(doc_seq);
	}
}
