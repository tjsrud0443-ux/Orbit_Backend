package com.study.app.domains.approval;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalService {

	@Autowired
	private ApprovalDAO dao;

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
	public void insertPayment(PaymentDTO dto) {
		insertCommonApprovalData(dto);
		dao.insertPaymentDetail(dto);
	}
	// 구매 신청서
	@Transactional
	public void insertPurchase(PurchaseDTO dto) {
		insertCommonApprovalData(dto);
		dao.insertPurchaseMaster(dto);
		
		Long purchase_seq = dto.getPurchase_seq();
		
		if (dto.getItems != null) {
		    for (PurchaseItemsDTO item : dto.getItems()) {
		        item.setPurchase_seq(purchase_seq);
		        dao.insertPurchaseItem(item);
		    }
		}
	}

}
