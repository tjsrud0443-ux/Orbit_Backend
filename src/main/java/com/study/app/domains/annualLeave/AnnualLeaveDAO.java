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

	public List<AnnualLeaveUpdateDTO> findOneYearUsers() {
		return mybatis.selectList("AnnualLeave.findOneYearUsers");
	}

	public void updateAnnualLeave(Long leaveSeq,double totalDays,
			double remainingDays) {

		Map<String, Object> params = new HashMap<>();
		params.put("leave_seq", leaveSeq);
		params.put("total_days", totalDays);
		params.put("remaining_days", remainingDays);

		mybatis.update("AnnualLeave.updateAnnualLeave",params);
	}
}
