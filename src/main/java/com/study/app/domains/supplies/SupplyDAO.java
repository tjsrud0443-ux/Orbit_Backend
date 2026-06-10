package com.study.app.domains.supplies;

import java.util.List;

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
	
	/*비품 신청 & 신청된 비품 리스트*/
	public int supplyRequest(SupplyRequestDTO dto) {
		return mybatis.insert("Supply.supplyRequest",dto);
	}
	
	public int supplyRequestItems(SupplyRequestItemsDTO dto) {
		return mybatis.insert("Supply.supplyRequestItems",dto);
	}
}
