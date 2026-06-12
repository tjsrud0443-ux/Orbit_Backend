package com.study.app.domains.overtimeRequest;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.app.domains.checkoutRequest.CheckoutRequestDTO;

@Repository
public class OvertimeRequestDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<OvertimeRequestDTO> getAllOvertimeRQ(Map<String, Object> param){
        return mybatis.selectList("OvertimeRequest.getAllOvertimeRQ", param);
    }

    public int getCount(String status){
        return mybatis.selectOne("OvertimeRequest.getCount", status);
    }

    public Map<String, Integer> getTabCount(){
        return mybatis.selectOne("OvertimeRequest.getTabCount");
    }
}
