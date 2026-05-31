package com.study.app.domains.approval;

import com.study.app.domains.admin.AdminController;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.users.UsersDTO;
import com.study.app.domains.users.UsersService;

@RestController
@RequestMapping("/approval")
public class ApprovalController {

	private final AdminController adminController;
	@Autowired
	private UsersService usersServ;
	@Autowired
	private ApprovalService appServ;

	ApprovalController(AdminController adminController) {
		this.adminController = adminController;
	}

	@GetMapping("all")
	public ResponseEntity<List<UsersDTO>> getAllEmployees(){
		List<UsersDTO> allEmployees = usersServ.getAllEmployees();
		return ResponseEntity.ok(allEmployees);
	}

	@PostMapping("submit/vacation")
	public ResponseEntity<Void> submitVacation(@RequestBody VacationDTO dto){ 
		appServ.insertVacation(dto);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("submit/general")
	public ResponseEntity<Void> submitGeneral(@RequestBody GeneralDTO dto){ 
		appServ.insertGeneral(dto);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "submit/purchase", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> submitPurchase(
			@RequestPart("dto") PurchaseDTO dto,
			@RequestPart(value = "files", required = false) List<MultipartFile> files){ 
		
		appServ.insertPurchase(dto, files);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value = "submit/payment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> submitPayment(
			@RequestPart("dto") PaymentDTO dto,
			@RequestPart(value = "files", required = false) List<MultipartFile> files){ 
		
		appServ.insertPayment(dto, files);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/detail/{doc_type}/{doc_seq}")
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/cc")
	public ResponseEntity<List<DraftDocumentsDTO>> getCcDocuments(@RequestAttribute String loginId) {
		List<DraftDocumentsDTO> list = appServ.getCcWithLine(loginId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/cc/page")
	public ResponseEntity<Map<String, Object>> getCcDocumentsByPage(
			@RequestAttribute String loginId, @RequestParam String status, @RequestParam Long cpage){
		Map<String, Object> result = appServ.getCcDocumentsByPage(loginId, status, cpage);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/myDoc")
	public ResponseEntity<List<DraftDocumentsDTO>> getMyDocuments(@RequestAttribute String loginId) {
		List<DraftDocumentsDTO> list = appServ.getMyDocWithLine(loginId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/myDoc/page")
	public ResponseEntity<Map<String, Object>> getMyDocumentsByPage(@RequestAttribute String loginId,
			@RequestParam String status, @RequestParam Long cpage) {
		Map<String, Object> result = appServ.getMyDocumentsByPage(loginId, status, cpage);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/MydraftDoc")
	public ResponseEntity<List<DraftDocumentsDTO>> getMydraftDoc(@RequestAttribute String loginId) {
		List<DraftDocumentsDTO> list = appServ.getMydraftDoc(loginId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/MyDoneDoc/page")
	public ResponseEntity<Map<String, Object>> getMyDoneDocByPage(@RequestAttribute String loginId,
			@RequestParam Long cpage) {
		Map<String, Object> result = appServ.getMyDoneDocByPage(loginId, cpage);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/temp")
	public ResponseEntity<List<DraftDocumentsDTO>> getTempDoc(@RequestAttribute String loginId) {
		List<DraftDocumentsDTO> result = appServ.getTempDoc(loginId);
		return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/tempDelete/{doc_seq}")
	public ResponseEntity<Void> deleteTempDoc(@PathVariable Long doc_seq, 
			@RequestParam String doc_type) {
		appServ.deleteTempDoc(doc_seq, doc_type);
		return ResponseEntity.ok().build();
	}
}
