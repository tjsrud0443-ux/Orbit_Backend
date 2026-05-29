package com.study.app.domains.approval;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApprovalDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void insertDraftDocument(DraftDocumentsDTO dto) {
		mybatis.insert("Approval.insertDraftDocument", dto);
	}
	
	public void insertApprovalLines(ApprovalLinesDTO dto) {
		mybatis.insert("Approval.insertApprovalLines", dto);
	}
	
	public void insertReferrers(ApprovalCcDTO dto) {
		mybatis.insert("Approval.insertReferrers", dto);
	}
	
	public void insertVacationDetail(VacationDTO dto) {
		mybatis.insert("Approval.insertVacationDetail", dto);
	}
	
	public void insertGeneralDetail(GeneralDTO dto) {
		mybatis.insert("Approval.insertGeneralDetail", dto);
	}
	
	public void insertPaymentDetail(PaymentDTO dto) {
		mybatis.insert("Approval.insertPaymentDetail", dto);
	}
	
	public void insertPaymentItem(PaymentItemsDTO dto) {
		mybatis.insert("Approval.insertPaymentItem", dto);
	}
	
	public void insertPurchaseDetail(PurchaseDTO dto) {
		mybatis.insert("Approval.insertPurchaseDetail", dto);
	}
	
	public void insertPurchaseItem(PurchaseItemsDTO dto) {
		mybatis.insert("Approval.insertPurchaseItem", dto);
	}
	
	public void insertPurchaseAttachment(PurchaseAttachmentsDTO dto) {
		mybatis.insert("Approval.insertPurchaseAttachment", dto);
	}
}
