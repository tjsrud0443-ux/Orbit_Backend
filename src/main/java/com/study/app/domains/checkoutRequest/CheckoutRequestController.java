package com.study.app.domains.checkoutRequest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkoutRQ")
public class CheckoutRequestController {
	
	@Autowired
	private CheckoutRequestService checkoutServ;
	
	@PostMapping("/insert")
	public Map<String, Object> insertCheckoutReq(@RequestBody CheckoutRequestDTO dto, @RequestAttribute String loginId) {
	    
	    Map<String, Object> result = new HashMap<>(); 
	    try {
	    	dto.setUsers_id(loginId);
	    	checkoutServ.insertCheckoutReq(dto);	        
	        
	        result.put("success", true);
	        result.put("message", "퇴근 정정 신청이 완료되었습니다.");
	        
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("message", "신청 중 오류가 발생했습니다.");
	        e.printStackTrace();
	    }
	    
	    return result;
	}
}
