package com.study.app.domains.certType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certType")
public class CertTypeController {
	
	@Autowired
	private CertTypeService certServ;
	
	@GetMapping("/getCertType")
	public ResponseEntity<List<CertTypeDTO>> getCertType(@RequestAttribute String loginId){
		List<CertTypeDTO> list = certServ.getCertType(loginId);
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/insertCertRequest")
	public ResponseEntity<Void> insertCertRequest(@RequestBody CertIssueRequestDTO dto, @RequestAttribute String loginId) {
		certServ.insertCertRequest(dto, loginId);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/cancelCertRequest/{cert_request_seq}")
	public ResponseEntity<Void> cancelCertRequest(@PathVariable Long cert_request_seq,
													@RequestAttribute String loginId) {
		certServ.cancelCertRequest(cert_request_seq, loginId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/increasePrintedCount/{cert_request_seq}")
	public ResponseEntity<Void> increasePrintedCount(@PathVariable Long cert_request_seq,
												@RequestAttribute String loginId) {
		certServ.increasePrintedCount(cert_request_seq, loginId);
		return ResponseEntity.ok().build();
	}
}
