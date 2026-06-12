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
	
	/*비품 신청 승인과 동시에 일어나는 작업들*/
	@Transactional
	public void approveRequest(SupplyRequestDTO srDto) {
		
		supplyDAO.updateRequestStatus(srDto);  // 신청 상태 변경	
		 // 신청 비품 수 만큼 비품 현재 재고 차감
		if ("APPROVED".equals(srDto.getStatus())) {
			//req에 없는 dept 정보 조회
			int deptSeq = supplyDAO.getDeptSeqByUserId(srDto.getUsers_id());
	        for (SupplyRequestItemsDTO item : srDto.getItems()) {
	            int result = supplyDAO.decreaseStock(item);
	            if (result == 0) {
	                throw new RuntimeException("재고가 부족합니다.");
	            }
	         // 대여 이력 INSERT 추가
	            supplyDAO.insertSupplyRental(
	            	new SupplyRentalDTO(item.getSupply_seq(), srDto.getUsers_id(), deptSeq, item.getEa(), srDto.getReq_date())
	            );
	        }
		} 
	}	
	
	@Transactional
	public void returnSupply(SupplyRentalDTO dto) {
	    supplyDAO.returnSupply(dto);
	    supplyDAO.increaseStock(dto);
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
