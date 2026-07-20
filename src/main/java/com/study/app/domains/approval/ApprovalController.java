package com.study.app.domains.approval;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.users.UsersDTO;

@RestController
@RequestMapping("/approval")
public class ApprovalController {

	@Autowired
	private ApprovalService appServ;

	@GetMapping("all")
	public ResponseEntity<List<UsersDTO>> getAllEmployees(){
		List<UsersDTO> allEmployees = appServ.getAllEmployees();
		return ResponseEntity.ok(allEmployees);
	}

	@PostMapping(value = "submit/vacation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> submitVacation(@RequestParam(required = false) Long originalDocSeq,
												@RequestPart("dto") VacationDTO dto,
												@RequestPart(value = "files", required = false) List<MultipartFile> files){ 
		try{
			appServ.insertVacation(dto, files, originalDocSeq);
			return ResponseEntity.ok("기안 상신이 완료되었습니다.");
		}catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping(value = "submit/general", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> submitGeneral(@RequestParam(required = false) Long originalDocSeq,
											 @RequestPart("dto") GeneralDTO dto,
											 @RequestPart(value = "files", required = false) List<MultipartFile> files){ 
		appServ.insertGeneral(dto, files, originalDocSeq);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "submit/purchase", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> submitPurchase(@RequestParam(required = false) Long originalDocSeq,
												@RequestPart("dto") PurchaseDTO dto,
												@RequestPart(value = "files", required = false) List<MultipartFile> files){ 
		
		appServ.insertPurchase(dto, files, originalDocSeq);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "submit/payment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> submitPayment(@RequestParam(required = false) Long originalDocSeq,
												@RequestPart("dto") PaymentDTO dto,
												@RequestPart(value = "files", required = false) List<MultipartFile> files){ 
		
		appServ.insertPayment(dto, files, originalDocSeq);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("detail/{doc_type}/{doc_seq}")
    public ResponseEntity<?> getDetail(@PathVariable String doc_type,
            							@PathVariable Long doc_seq) {
		
		try {
			Map<String, Object> result = appServ.getApprovalDetail(doc_type.toUpperCase(), doc_seq);
			return ResponseEntity.ok(result);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}    

	@PutMapping("approve/{doc_seq}")
	public ResponseEntity<Void> approveDraft(@PathVariable Long doc_seq,
												@RequestAttribute String loginId,
												@RequestParam String doc_type){
		appServ.approveDraft(doc_seq, loginId, doc_type);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("reject/{doc_seq}")
	public ResponseEntity<Void> rejectApproval(@PathVariable Long doc_seq,
												@RequestAttribute String loginId,
												@RequestBody Map<String, Object> data){
		
		String reject_reason = String.valueOf(data.get("reject_reason"));
		appServ.rejectApproval(doc_seq, loginId, reject_reason);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("update/vacation/{doc_seq}")
	public ResponseEntity<Void> updateVacation(@PathVariable Long doc_seq,
												@RequestPart("dto") VacationDTO dto,
												@RequestPart(value = "files", required = false) List<MultipartFile> files){
		
		appServ.updateVacation(doc_seq, dto, files);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("update/general/{doc_seq}")
	public ResponseEntity<Void> updateGeneral(@PathVariable Long doc_seq,
												@RequestPart("dto") GeneralDTO dto,
												@RequestPart(value = "files", required = false) List<MultipartFile> files){
		
		appServ.updateGeneral(doc_seq, dto, files);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("update/payment/{doc_seq}")
	public ResponseEntity<Void> updatePayment(@PathVariable Long doc_seq,
												@RequestPart("dto") PaymentDTO dto,
												@RequestPart(value = "files", required = false) List<MultipartFile> files){
		
		appServ.updatePayment(doc_seq, dto, files);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("update/purchase/{doc_seq}")
	public ResponseEntity<Void> updatePurchase(@PathVariable Long doc_seq,
												@RequestPart("dto") PurchaseDTO dto,
												@RequestPart(value = "files", required = false) List<MultipartFile> files){
		
		appServ.updatePurchase(doc_seq, dto, files);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/home")
	public ResponseEntity<Map<String, Object>> getApprovalHomeData(@RequestAttribute String loginId) {
	    return ResponseEntity.ok(appServ.getApprovalHomeData(loginId));
	}
	
	@GetMapping("/cc")
	public ResponseEntity<List<DraftDocumentsDTO>> getCcDocuments(@RequestAttribute String loginId) {
		List<DraftDocumentsDTO> list = appServ.getCcWithLine(loginId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/cc/page")
	public ResponseEntity<Map<String, Object>> getCcDocumentsByPage(
			@RequestAttribute String loginId, @RequestParam String status, @RequestParam Long cpage,
			@RequestParam String keyword, @RequestParam String docType){
		Map<String, Object> result = appServ.getCcDocumentsByPage(loginId, status, cpage, keyword, docType);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/myDoc")
	public ResponseEntity<List<DraftDocumentsDTO>> getMyDocuments(@RequestAttribute String loginId) {
		List<DraftDocumentsDTO> list = appServ.getMyDocWithLine(loginId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/myDoc/page")
	public ResponseEntity<Map<String, Object>> getMyDocumentsByPage(@RequestAttribute String loginId,
			@RequestParam String status, @RequestParam Long cpage,
			@RequestParam String keyword, @RequestParam String docType) {
		Map<String, Object> result = appServ.getMyDocumentsByPage(loginId, status, cpage, keyword, docType);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/MydraftDoc")
	public ResponseEntity<List<DraftDocumentsDTO>> getMydraftDoc(@RequestAttribute String loginId) {
		List<DraftDocumentsDTO> list = appServ.getMydraftDoc(loginId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/MyDoneDoc/page")
	public ResponseEntity<Map<String, Object>> getMyDoneDocByPage(@RequestAttribute String loginId,
			@RequestParam Long cpage, @RequestParam String keyword, @RequestParam String docType) {
		Map<String, Object> result = appServ.getMyDoneDocByPage(loginId, cpage, keyword, docType);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/temp")
	public ResponseEntity<List<DraftDocumentsDTO>> getTempDoc(@RequestAttribute String loginId) {
		List<DraftDocumentsDTO> result = appServ.getTempDoc(loginId);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/delete/{doc_seq}")
	public ResponseEntity<Void> deleteDoc(@PathVariable Long doc_seq, 
			@RequestParam String doc_type) {
		appServ.deleteDoc(doc_seq, doc_type);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("topReferrers")
	public ResponseEntity<List<UsersDTO>> getTopReferrers(@RequestAttribute String loginId) {
		List<UsersDTO> list = appServ.getTopReferrers(loginId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("vacationTypes")
	public ResponseEntity<List<VacationTypesDTO>> getAllVacationTypes() {
		List<VacationTypesDTO> list = appServ.getAllVacationTypes();
		return ResponseEntity.ok(list);
	}
	
	@PutMapping("/bulkApproveDocuments")
	public ResponseEntity<Map<String, Object>> bulkApproveDocuments(@RequestAttribute String loginId,
												@RequestBody List<Long> docSeqList) {
		int approvalCount = appServ.bulkApproveDocuments(loginId, docSeqList);
		return ResponseEntity.ok(Map.of("approved_count", approvalCount));
	}
	
	@GetMapping("defaultApprovalLine")
	public ResponseEntity<List<UsersDTO>> getDefaultApprovers(@RequestParam String doc_type,
																@RequestAttribute String loginId) {
		
		List<UsersDTO> approvers = appServ.getDefaultApprovers(doc_type, loginId);
		return ResponseEntity.ok(approvers);
	}
}
