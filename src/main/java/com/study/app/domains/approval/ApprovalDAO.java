package com.study.app.domains.approval;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApprovalDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertDraftDocument(DraftDocumentsDTO dto) {
		return mybatis.insert("Approval.insertDraftDocument", dto);
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
	
	public int insertGeneralDetail(GeneralDTO dto) {
		return mybatis.insert("Approval.insertGeneralDetail", dto);
	}
}
