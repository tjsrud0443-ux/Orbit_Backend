package com.study.app.domains.supplies;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SupplyDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	/*비품 관리*/
	public List<SupplyDTO> getSupplyList(){
		return mybatis.selectList("Supply.getSupplyList");
	}
	
	public List<SupplyRequestDTO> getAdminRequestList(Map<String, Object> params){
		return mybatis.selectList("Supply.getAdminRequestList",params);
	}
	
	public int getAdminRequestCount(Map<String, Object> params) {
		return mybatis.selectOne("Supply.getAdminRequestCount",params);
	}
	
	// 신청 승인과 동시에 비품 신청 상태 수정
	public int updateRequestStatus(SupplyRequestDTO dto) {
		return mybatis.update("Supply.updateRequestStatus", dto);
	}
	
	// 신청 승인과 동시에 비품 재고 수정
	public int decreaseStock(SupplyRequestItemsDTO dto) {
		return mybatis.update("Supply.decreaseStock",dto);
	}
	
	/*비품 대여 이력 관리*/
	// 유저 DEPT_SEQ 조회
	public int getDeptSeqByUserId(String users_id) {
	    return mybatis.selectOne("Supply.getDeptSeqByUserId", users_id);
	}

	// 신청 승인과 동시에 대여 이력 INSERT
	public void insertSupplyRental(SupplyRentalDTO dto) {
	    mybatis.insert("Supply.insertSupplyRental", dto);
	}
	public SupplyRequestDTO getReqBySeq(Long req_seq) {
	    return mybatis.selectOne("Supply.getReqBySeq", req_seq);
	}
	//대여이력 출력
	public List<SupplyRentalDTO> supplyRentalList(Map<String, Object> params) {
	    return mybatis.selectList("Supply.supplyRentalList", params);
	}

	public int supplyRentalCount(Map<String, Object> params) {
	    return mybatis.selectOne("Supply.supplyRentalCount", params);
	}
	
	
	 // 대여 이력 반납 날짜 update // 
	public int returnSupply(SupplyRentalDTO dto) { 
		return mybatis.update("Supply.returnSupply",dto);  
	} 
	//재고 다시 복구 //
	 public int increaseStock(SupplyRentalDTO dto) {  
		 return mybatis.update("Supply.increaseStock",dto);  
	 }
	 
	/*비품 신청 & 신청된 비품 리스트*/
	public int supplyRequest(SupplyRequestDTO dto) {
		return mybatis.insert("Supply.supplyRequest",dto);
	}
	
	public int supplyRequestItems(SupplyRequestItemsDTO dto) {
		return mybatis.insert("Supply.supplyRequestItems",dto);
	}
}
