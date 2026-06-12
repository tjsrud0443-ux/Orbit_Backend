package com.study.app.domains.checkoutRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.signup.SignupDTO;

@Service
public class CheckoutRequestService {
	
	@Autowired
	private CheckoutRequestDAO dao;
	
	public Map<String, Object> getAllCheckoutRQ(Long cPage, String status) {
		int recordCountPerPage = 10;

	    Long start = (cPage - 1) * recordCountPerPage + 1;
	    Long end = cPage * recordCountPerPage;

	    Map<String, Object> param = new HashMap<>();

	    param.put("start", start);
	    param.put("end", end);
	    param.put("status", status);

	    List<CheckoutRequestDTO> list = dao.getAllCheckoutRQ(param);

	    int totalCount = dao.getCount(status);

	    Map<String, Integer> tabCount = dao.getTabCount();

	    Map<String, Object> result = new HashMap<>();

	    result.put("list", list);
	    result.put("count", totalCount);
	    result.put("tabCount", tabCount);

	    return result;
    }
}
