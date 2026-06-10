package com.study.app.domains.supplies;

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
@RequestMapping("/supply")
public class SupplyController {
	
	@Autowired
	private SupplyService supplyServ;
	
	//비품 리스트 > 비품관리에 그대로 써도 됨
	@GetMapping
	public ResponseEntity<List<SupplyDTO>> getSupplyList(){
		List<SupplyDTO> supplies = supplyServ.getSupplyList();
		return ResponseEntity.ok(supplies);
	}
	
	//비품 신청 & 신청 비품 리스트 관련
	@PostMapping("/request")
	public ResponseEntity<Void> supplyRequest(@RequestAttribute String loginId, @RequestBody SupplyRequestDTO dto){
		dto.setUsers_id(loginId);
		supplyServ.supplyRequest(dto);
		return ResponseEntity.ok().build();
	}
}
