package com.study.app.domains.approval;

import java.util.List;
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
		System.out.println("docSeq : " + docSeq);

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
		System.out.println("dto : " + dto);
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
	public void insertPayment(PaymentDTO dto) {
		insertCommonApprovalData(dto);
		dao.insertPaymentDetail(dto);
	}
	// 구매 신청서
	@Transactional
	public void insertPurchase(PurchaseDTO dto, List<MultipartFile> files) {
		insertCommonApprovalData(dto);
		dao.insertPurchaseMaster(dto);
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
						
						BlobId blobId = BlobId.of(bucketName, sysname);
						BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
											.setContentType(file.getContentType()).build();
						
						// GCS 업로드
						storage.create(blobInfo, file.getBytes());
						
						PurchaseAttachmentsDTO attach = new PurchaseAttachmentsDTO();
						attach.setPurchase_seq(purchase_seq);
						attach.setOriname(oriname);
						attach.setSysname(sysname);
						
						dao.insertPurchaseAttachment(attach);
						
					}catch(Exception e) {
						e.printStackTrace();
						System.out.println(file.getOriginalFilename() + "업로드 실패");
						throw new RuntimeException(file.getOriginalFilename() + " 파일 업로드 중 오류 발생", e);
					}
				}
			}
		}
	}

}
