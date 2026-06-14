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
	
	public void insertSupply(SupplyDTO dto) {
		 // 음수 입력 방지
	    if (dto.getTotal_qty() < 0 || dto.getMin_stock_qty() < 0) {
	        throw new IllegalArgumentException("수량은 0 이상이어야 합니다.");
	    }
	    int count = supplyDAO.checkDupCode(dto.getSupply_code());
	    if (count > 0) {
	        throw new RuntimeException("이미 존재하는 비품코드입니다.");
	    }
	    supplyDAO.insertSupply(dto);
	}
	
	public void updateSupplies(SupplyDTO dto) {
		 // 음수 입력 방지
	    if (dto.getTotal_qty() < 0 || dto.getMin_stock_qty() < 0) {
	        throw new IllegalArgumentException("수량은 0 이상이어야 합니다.");
	    }
		//수정 전 데이터를 current 변수에 담아둠
	    SupplyDTO current = supplyDAO.selectSupplyBySeq(dto.getSupply_seq());
	    //			수정 후 총 재고수량 - 원래 재고수량
	    long diff = dto.getTotal_qty() - current.getTotal_qty();
	    //현재 재고수량 + 수량 
	    long newStockQty = current.getStock_qty() + diff;
	    //자동 반영된 현재 재고수량이 0 이하면 경고
	    if (newStockQty < 0) {
	        throw new IllegalArgumentException("현재 재고가 0 미만이 될 수 없습니다.");
	    }

	    dto.setStock_qty(newStockQty);
	    supplyDAO.updateSupplies(dto);
	}
	
	//admin
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
	
	/*mypage myRequestList*/
	@Transactional
	public void deleteMySupplyRequest(Long req_seq) {
	    supplyDAO.deleteMySupplyReqItems(req_seq); // 자식 먼저
	    supplyDAO.deleteMySupplyRequest(req_seq);       // 부모 나중에
	}
}
