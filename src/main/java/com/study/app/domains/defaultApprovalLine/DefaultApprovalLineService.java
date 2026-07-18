package com.study.app.domains.defaultApprovalLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultApprovalLineService {
	
	@Autowired
	private DefaultApprovalLineDAO dao;
	
	public List<DefaultApprovalLineDTO> getApprovalLines(String doc_type) {
		return dao.getApprovalLines(doc_type);
	}
	
	public void saveApprovalLines(List<DefaultApprovalLineDTO> lines, String doc_type, Long drafter_rank_seq) {
		Map<String, Object> params = new HashMap<>();
		params.put("doc_type", doc_type);
		params.put("drafter_rank_seq", drafter_rank_seq);
		dao.deleteOriDefaultLine(params);
		
		for (int i = 0; i < lines.size(); i++) {
			DefaultApprovalLineDTO line = lines.get(i);
	        line.setDoc_type(doc_type);
	        line.setDrafter_rank_seq(drafter_rank_seq);
	        line.setStep_order(Long.valueOf(i + 1));
	        dao.insertApprovalLine(line);
		}
	}
	
	public void deleteApprovalLine(String doc_type, Long drafter_rank_seq) {
		dao.deleteApprovalLine(doc_type, drafter_rank_seq);
	}
}
