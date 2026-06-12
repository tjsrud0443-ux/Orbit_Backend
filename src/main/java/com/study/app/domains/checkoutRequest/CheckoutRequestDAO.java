package com.study.app.domains.checkoutRequest;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.app.domains.signup.SignupDTO;

@Repository
public class CheckoutRequestDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<CheckoutRequestDTO> getAllCheckoutRQ(Map<String, Object> param){
        return mybatis.selectList("CheckoutRequest.getAllCheckoutRQ", param);
    }

    public int getCount(String status){
        return mybatis.selectOne("CheckoutRequest.getCount", status);
    }

    public Map<String, Integer> getTabCount(){
        return mybatis.selectOne("CheckoutRequest.getTabCount");
    }
}
