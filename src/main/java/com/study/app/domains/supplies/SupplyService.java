package com.study.app.domains.supplies;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplyService {
	@Autowired
	private SupplyDAO supplyDAO;
	
	/*admin supply*/
	public List<SupplyDTO> getSupplyList(){
		return supplyDAO.getSupplyList();
	}
	public List<SupplyRequestDTO> getAdminRequestList(Map<String, Object> params){
		return supplyDAO.getAdminRequestList(params);
	}
	
	@Transactional
	public void approveRequest(SupplyRequestDTO srDto) {
		
		supplyDAO.updateRequestStatus(srDto);  // 상태 변경	
		 // 승인일 때만 재고 차감
		if ("APPROVED".equals(srDto.getStatus())) {
	        for (SupplyRequestItemsDTO item : srDto.getItems()) {
	            int result = supplyDAO.decreaseStock(item);
	            if (result == 0) {
	                throw new RuntimeException("재고가 부족합니다.");
	            }
	        }
		} 
	}	
	
	/*supply request*/
	@Transactional
	public void supplyRequest(SupplyRequestDTO dto) {
		supplyDAO.supplyRequest(dto);  // req_seq가 dto에 세팅됨
	    for (SupplyRequestItemsDTO item : dto.getItems()) {
	        item.setReq_seq(dto.getReq_seq());
	        supplyDAO.supplyRequestItems(item);
	    }
	}
}
