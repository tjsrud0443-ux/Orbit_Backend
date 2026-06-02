package com.study.app.domains.approval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public Map<String, Object> selectCommonDetail(Long doc_seq){
		return mybatis.selectOne("Approval.selectCommonDetail", doc_seq);
	}
	
	public List<Map<String, Object>> selectApprovers(Long doc_seq){
		return mybatis.selectList("Approval.selectApprovers", doc_seq);
	}
	
	public List<Map<String, Object>> selectReferrers(Long doc_seq){
		return mybatis.selectList("Approval.selectReferrers", doc_seq);
	}
	
	public Map<String, Object> selectVacationDetail(Long doc_seq){
		return mybatis.selectOne("Approval.selectVacationDetail", doc_seq);
	}
	
	public Map<String, Object> selectGeneralDetail(Long doc_seq){
		return mybatis.selectOne("Approval.selectGeneralDetail", doc_seq);
	}
	
	public Map<String, Object> selectPaymentDetail(Long doc_seq){
		return mybatis.selectOne("Approval.selectPaymentDetail", doc_seq);
	}
	
	public Map<String, Object> selectPurchaseDetail(Long doc_seq){
		return mybatis.selectOne("Approval.selectPurchaseDetail", doc_seq);
	}
	
	public List<Map<String, Object>> selectPaymentItems(Long pay_seq){
		return mybatis.selectList("Approval.selectPaymentItems", pay_seq);
	}
	
	public List<Map<String, Object>> selectPurchaseItems(Long purchase_seq){
		return mybatis.selectList("Approval.selectPurchaseItems", purchase_seq);
	}
	
	public List<Map<String, Object>> selectPurchaseAttachments(Long purchase_seq){
		return mybatis.selectList("Approval.selectPurchaseAttachments", purchase_seq);
	}
	
	public Map<String, Object> selectMyApprovalLine(Long doc_seq, String loginId) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("doc_seq", doc_seq);
	    params.put("users_id", loginId);
	    return mybatis.selectOne("Approval.selectMyApprovalLine", params);
	}

	public Map<String, Object> selectNextApprovalLine(Long doc_seq, Long currentStepOrder) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("doc_seq", doc_seq);
	    params.put("step_order", currentStepOrder);
	    return mybatis.selectOne("Approval.selectNextApprovalLine", params);
	}

	public void updateApprovalLineStatus(Long doc_seq, String loginId, String status) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("doc_seq", doc_seq);
	    params.put("users_id", loginId);
	    params.put("status", status);
	    mybatis.update("Approval.updateApprovalLineStatus", params);
	}
	
	public void updateNextApprovalLineStatus(Long doc_seq, String loginId, String status) {
		Map<String, Object> params = new HashMap<>();
		params.put("doc_seq", doc_seq);
		params.put("users_id", loginId);
		params.put("status", status);
		mybatis.update("Approval.updateNextApprovalLineStatus", params);
	}

	public void updateDocumentStatus(Long doc_seq, String status) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("doc_seq", doc_seq);
	    params.put("status", status);
	    mybatis.update("Approval.updateDocumentStatus", params);
	}
	
	public void updateApprovalLineStatusToReject(Long doc_seq, String loginId, String status, String reject_reason) {
		Map<String, Object> params = new HashMap<>();
		params.put("doc_seq", doc_seq);
		params.put("users_id", loginId);
		params.put("status", status);
		params.put("reject_reason", reject_reason);
		mybatis.update("Approval.updateApprovalLineStatusToReject", params);
	}
	
	public void updateDocument(Long doc_seq, String status, String reject_reason) {
		Map<String, Object> params = new HashMap<>();
	    params.put("doc_seq", doc_seq);
	    params.put("status", status);
	    params.put("reject_reason", reject_reason);
	    mybatis.update("Approval.updateDocumentStatusToReject", params);
	}
	
	public Map<String, Object> selectVacationDays(Long doc_seq){
		return mybatis.selectOne("Approval.selectVacationDays", doc_seq);
	}
	
	public void updateDraftDocument(DraftDocumentsDTO dto) {
		mybatis.update("Approval.updateDraftDocument", dto);
	}
	
	public void deleteApprovalLines(Long doc_seq) {
		mybatis.delete("Approval.deleteApprovalLines", doc_seq);
	}
	
	public void deleteReferrers(Long doc_seq) {
		mybatis.delete("Approval.deleteReferrers", doc_seq);
	}
	
	public void updateVacationDetail(VacationDTO dto) {
		mybatis.update("Approval.updateVacationDetail", dto);
	}
	
	public void updateGeneralDetail(GeneralDTO dto) {
		mybatis.update("Approval.updateGeneralDetail", dto);
	}
	
	public Long selectPay_seq(Long doc_seq) {
		return mybatis.selectOne("Approval.selectPay_seq", doc_seq);
	}
	
	public void deletePaymentItems(Long pay_seq) {
		mybatis.delete("Approval.deletePaymentItems", pay_seq);
	}
	
	public void updatePaymentDetail(PaymentDTO dto) {
		mybatis.update("Approval.updatePaymentDetail", dto);
	}
	
	public Long selectPurchase_seq(Long doc_seq) {
		return mybatis.selectOne("Approval.selectPurchase_seq", doc_seq);
	}
	
	public void deletePurchaseItems(Long purchase_seq) {
		mybatis.delete("Approval.deletePurchaseItems", purchase_seq);
	}
	
	public void deletePurchaseAttachments(Long purchase_seq) {
		mybatis.delete("Approval.deletePurchaseAttachments", purchase_seq);
	}
	
	public void updatePurchaseDetail(PurchaseDTO dto) {
		mybatis.update("Approval.updatePurchaseDetail", dto);
	}
	
	public int countPendingApprovals(String users_id) {
		return mybatis.selectOne("Approval.countPendingApprovals", users_id);
	}
	
	public int countInProgress(String users_id) {
		return mybatis.selectOne("Approval.countInProgress", users_id);
	}
	
	public int countApproved(String users_id) {
		return mybatis.selectOne("Approval.countApproved", users_id);
	}
	
	public int countRejected(String users_id) {
		return mybatis.selectOne("Approval.countRejected", users_id);
	}
	
	public List<Map<String, Object>> selectRecentDocs(String users_id){
		return mybatis.selectList("Approval.selectRecentDocs", users_id);
	}
	
	public List<DraftDocumentsDTO> getCcList(String loginId) {
		return mybatis.selectList("Approval.getCcList", loginId);
	}
	
	public List<ApprovalLinesDTO> getLinesBySeq(Long doc_seq){
		return mybatis.selectList("Approval.getLinesBySeq", doc_seq);
	}
	
	public List<DraftDocumentsDTO> getCcPage(Map<String, Object> param) {
		return mybatis.selectList("Approval.getCcPage", param);
	}
	
	public int getCcpageCount(Map<String, Object> param) {
		return mybatis.selectOne("Approval.getCcpageCount", param);
	}
	
	public List<DraftDocumentsDTO> getMyDoc(String loginId) {
		return mybatis.selectList("Approval.getMyDoc", loginId);
	}
	
	public List<DraftDocumentsDTO> getMyDocPage(Map<String, Object> param) {
		return mybatis.selectList("Approval.getMyDocPage", param);
	}
	
	public int getMyDocPageCount(Map<String, Object> param) {
		return mybatis.selectOne("Approval.getMyDocPageCount", param);
	}
	
	public List<DraftDocumentsDTO> getMydraftDoc(String loginId) {
		return mybatis.selectList("Approval.getMydraftDoc", loginId);
	}
	
	public List<DraftDocumentsDTO> getMyDoneDocByPage(Map<String, Object> param) {
		return mybatis.selectList("Approval.getMyDoneDocByPage", param);
	}
	
	public int getMyDoneDocCount(Map<String, Object> param) {
		return mybatis.selectOne("Approval.getMyDoneDocCount", param);
	}
	
	public List<DraftDocumentsDTO> getTempDoc(String loginId) {
		return mybatis.selectList("Approval.getTempDoc", loginId);
	}
	
	public List<DraftDocumentsDTO> tempList() {
		return mybatis.selectList("Approval.tempList");
	}
	
	public void deleteAppLine(Long doc_seq) {
		mybatis.delete("Approval.deleteAppLine", doc_seq);
	}
	
	public void deleteAppCc(Long doc_seq) {
		mybatis.delete("Approval.deleteAppCc", doc_seq);
	}
	
	public void deleteDraftDoc(Long doc_seq) {
		mybatis.delete("Approval.deleteDraftDoc", doc_seq);
	}
	
	public void deleteVacDoc(Long doc_seq) {
		mybatis.delete("Approval.deleteVacDoc", doc_seq);
	}
	
	public void deleteGenDoc(Long doc_seq) {
		mybatis.delete("Approval.deleteGenDoc", doc_seq);
	}
	
	public void deletePayItem(Long doc_seq) {
		mybatis.delete("Approval.deletePayItem", doc_seq);
	}
	
	public void deletePayDoc(Long doc_seq) {
		mybatis.delete("Approval.deletePayDoc", doc_seq);
	}
	
	public void deletePurAttach(Long doc_seq) {
		mybatis.delete("Approval.deletePurAttach", doc_seq);
	}
	
	public void deletePurItem(Long doc_seq) {
		mybatis.delete("Approval.deletePurItem", doc_seq);
	}
	
	public void deletePurDoc(Long doc_seq) {
		mybatis.delete("Approval.deletePurDoc", doc_seq);
	}
	
}
