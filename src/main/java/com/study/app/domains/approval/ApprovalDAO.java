package com.study.app.domains.approval;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApprovalDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertDraftOfVacation(DraftDocumentsDTO dto) {
		return mybatis.insert("Approval.insertDraftOfVacation", dto);
	}
	
	public int insertApprovalLines(ApprovalLinesDTO dto) {
		return mybatis.insert("Approval.insertApprovalLines", dto);
	}
	
	public int insertReferrers(ApprovalCcDTO dto) {
		return mybatis.insert("Approval.insertReferrers", dto);
	}
	
	public int insertVacationDetail(VacationDTO dto) {
		return mybatis.insert("Approval.insertVacationDetail", dto);
	}
}
