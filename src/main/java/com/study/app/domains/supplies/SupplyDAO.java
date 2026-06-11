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
	
	//비품 신청 상태 수정
	public int updateRequestStatus(SupplyRequestDTO dto) {
		return mybatis.update("Supply.updateRequestStatus", dto);
	}
	
	//비품 재고 수정
	public int decreaseStock(SupplyRequestItemsDTO dto) {
		return mybatis.update("Supply.decreaseStock",dto);
	}
	
	/*비품 신청 & 신청된 비품 리스트*/
	public int supplyRequest(SupplyRequestDTO dto) {
		return mybatis.insert("Supply.supplyRequest",dto);
	}
	
	public int supplyRequestItems(SupplyRequestItemsDTO dto) {
		return mybatis.insert("Supply.supplyRequestItems",dto);
	}
}
