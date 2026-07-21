package com.study.app.domains.certType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<List<CertTypeDTO>> getCertType(){
		List<CertTypeDTO> list = certServ.getCertType();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/insertCertRequest")
	public ResponseEntity<Void> insertCertRequest(@RequestBody CertIssueRequestDTO dto, @RequestAttribute String loginId) {
		certServ.insertCertRequest(dto, loginId);
		return ResponseEntity.ok().build();
	}
}
