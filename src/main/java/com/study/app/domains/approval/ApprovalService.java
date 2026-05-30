package com.study.app.domains.approval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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





















































































































































	public List<DraftDocumentsDTO> getCcWithLine(String loginId) {
		List<DraftDocumentsDTO> ccList = dao.getCcList(loginId);

		for(DraftDocumentsDTO draftDoc : ccList) {
			List<ApprovalLinesDTO> lines = dao.getLinesBySeq(draftDoc.getDoc_seq());

			draftDoc.setApprovers(lines);			
		}

		return ccList;
	}

	public Map<String, Object> getCcDocumentsByPage(String loginId, String status, Long cpage){
		int start = (int)(cpage * 5 - 4);
		int end = (int)(cpage * 5);
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("loginId", loginId);
		param.put("status", status);
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
}
