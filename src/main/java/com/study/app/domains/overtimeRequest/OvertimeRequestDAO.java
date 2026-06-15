package com.study.app.domains.overtimeRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    
    public OvertimeRequestDTO getOvertimeInfo(Long overtime_seq) {
    	return mybatis.selectOne("OvertimeRequest.getOvertimeInfo", overtime_seq);
    }
    
    public void approveOvertime(Long overtime_seq, String loginId) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("overtime_seq", overtime_seq);
    	params.put("approver_id", loginId);
    	mybatis.update("OvertimeRequest.approveOvertime", params);
    }
    
    public void rejectOvertime(Long overtime_seq, String loginId) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("overtime_seq", overtime_seq);
    	params.put("approver_id", loginId);
    	mybatis.update("OvertimeRequest.rejectOvertime", params);
    }
    
    public void insertOvertimeReq(OvertimeRequestDTO dto) {
    	mybatis.insert("OvertimeRequest.insertOvertimeReq",dto);
    }
}
