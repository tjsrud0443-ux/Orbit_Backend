package com.study.app.domains.supplies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplyService {
	@Autowired
	private SupplyDAO supplyDAO;
	
	public List<SupplyDTO> getSupplyList(){
		return supplyDAO.getSupplyList();
	}
	
	@Transactional
	public void supplyRequest(SupplyRequestDTO dto) {
		supplyDAO.supplyRequest(dto);  // req_seq가 dto에 세팅됨
	    for (SupplyRequestItemsDTO item : dto.getItems()) {
	        item.setReq_seq(dto.getReq_seq());
	        supplyDAO.supplyRequestItems(item);
	    }
	}
}
