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
}
