package com.study.app.domains.approval;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TempDocScheduler {
	
	@Autowired
	private ApprovalService appServ;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void deleteTempDoc() {
		
		List<DraftDocumentsDTO> result = appServ.tempList();
		
		for(DraftDocumentsDTO dto : result) {
			appServ.deleteDoc(dto.getDoc_seq(), dto.getDoc_type());
		}
	}
}
