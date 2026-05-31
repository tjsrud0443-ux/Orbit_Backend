package com.study.app.domains.approval;

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
