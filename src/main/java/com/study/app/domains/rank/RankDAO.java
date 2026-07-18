package com.study.app.domains.rank;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RankDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<RankDTO> getRankList(){
		return mybatis.selectList("Rank.getRankList");
	}
	
	public void insertRank(RankDTO dto) {
		mybatis.insert("Rank.insertRank", dto);
	}
	
	public void updateRank(RankDTO dto) {
		mybatis.update("Rank.updateRank", dto);
	}
	
	public int countUsersByRank(Long rank_seq) {
		return mybatis.selectOne("Rank.countUsersByRank", rank_seq);
	}
	
	public void deleteDefaultApprovalLineByRankSeq(Long rank_seq) {
		mybatis.delete("Rank.deleteDefaultApprovalLineByRankSeq", rank_seq);
	}
	
	public int deleteRank(Long rank_seq) {
		return mybatis.delete("Rank.deleteRank", rank_seq);
	}
	
	public void reorderRanks() {
		mybatis.update("Rank.reorderRanks");
	}
	
	public int updateRankOrder(RankDTO dto) {
		return mybatis.update("Rank.updateRankOrder",dto);
	}
}
