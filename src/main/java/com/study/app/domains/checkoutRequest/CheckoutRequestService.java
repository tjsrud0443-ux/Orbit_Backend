package com.study.app.domains.checkoutRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.app.domains.attendance.AttendanceService;

@Service
public class CheckoutRequestService {
	
	@Autowired
	private CheckoutRequestDAO dao;
	@Autowired
	private AttendanceService attServ;
	
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
	
	@Transactional
	public void approveCheckout(Long checkout_seq, String loginId) {
		CheckoutRequestDTO req = dao.getCheckoutInfo(checkout_seq);
		attServ.changeCheckout(req);
		dao.approveCheckout(checkout_seq, loginId);
	}
	
	public void rejectCheckout(Long checkout_seq, String loginId) {
		dao.rejectCheckout(checkout_seq, loginId);
	}
}
