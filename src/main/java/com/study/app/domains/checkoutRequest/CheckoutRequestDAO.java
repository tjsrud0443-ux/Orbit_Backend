package com.study.app.domains.checkoutRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CheckoutRequestDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<CheckoutRequestDTO> getAllCheckoutRQ(Map<String, Object> param) {
        return mybatis.selectList("CheckoutRequest.getAllCheckoutRQ", param);
    }

    public int getCount(String status) {
        return mybatis.selectOne("CheckoutRequest.getCount", status);
    }

    public Map<String, Integer> getTabCount() {
        return mybatis.selectOne("CheckoutRequest.getTabCount");
    }
    
    public void approveCheckout(Long checkout_seq, String loginId) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("checkout_seq", checkout_seq);
    	params.put("approver_id", loginId);
    	mybatis.update("CheckoutRequest.approveCheckout", params);
    }
    
    public CheckoutRequestDTO getCheckoutInfo(Long checkout_seq) {
    	return mybatis.selectOne("CheckoutRequest.getCheckoutInfo", checkout_seq);
    }
    
    public void rejectCheckout(Long checkout_seq, String loginId) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("checkout_seq", checkout_seq);
    	params.put("approver_id", loginId);
    	mybatis.update("CheckoutRequest.rejectCheckout", params);
    }
    
	public void insertCheckoutReq(CheckoutRequestDTO dto) {
		mybatis.insert("CheckoutRequest.insertCheckoutReq",dto);
	}
	
}
