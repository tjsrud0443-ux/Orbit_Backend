package com.study.app.domains.annualLeave;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AnnualLeaveDAO {

	@Autowired
	private SqlSessionTemplate mybatis;

	public void insertAnnualLeave(String users_id) {
		mybatis.insert("AnnualLeave.insertAnnualLeave", users_id);
	}

	public Map<String, Object> selectAnnualLeaveData(String users_id){
		return mybatis.selectOne("AnnualLeave.selectAnnualLeaveData", users_id);
	}

	public void updateAnnualLeaveUsedDays(String users_id, Double newUsed, Double newRemaining) {
		Map<String, Object> params = new HashMap<>();
		params.put("users_id", users_id);
		params.put("used_days", newUsed);
		params.put("remaining_days", newRemaining);
		mybatis.update("AnnualLeave.updateAnnualLeaveUsedDays", params);
	}

	public AnnualLeaveDTO getAnnualLeaveSummary(String loginId) {
		return mybatis.selectOne("AnnualLeave.getAnnualLeaveSummary",loginId);
	}

	public List<AnnualLeaveUpdateDTO> findAnnualLeaveTargetUsers() {
		return mybatis.selectList("AnnualLeave.findAnnualLeaveTargetUsers");
	}
	
	public int insertMissingAnnualLeave() {
		return mybatis.insert("AnnualLeave.insertMissingAnnualLeave");
	}

	public void updateAnnualLeave(Long leaveSeq,double totalDays,
			double remainingDays) {

		Map<String, Object> params = new HashMap<>();
		params.put("leave_seq", leaveSeq);
		params.put("total_days", totalDays);
		params.put("remaining_days", remainingDays);

		mybatis.update("AnnualLeave.updateAnnualLeave",params);
	}
	
	//연차 관리
	public List<AdminLeaveDTO> getAllLeaveList(Map<String, Object> params) {
		return mybatis.selectList("AnnualLeave.getAllLeaveList", params);
	}
	
	public int getLeaveCount(Map<String, Object> params) {
	    return mybatis.selectOne("AnnualLeave.getLeaveCount", params);
	}
	
	public int updateAdminLeave(Map<String, Object> params) {
		return mybatis.update("AnnualLeave.updateAdminLeave",params);
	}
	
	public AdminLeaveDTO getLeaveBySeq(Long leaveSeq) {
	    return mybatis.selectOne("AnnualLeave.getLeaveBySeq", leaveSeq);
	}
}
