package com.study.app.domains.certType;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CertTypeDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<CertTypeDTO> getCertType(String loginId) {
		return mybatis.selectList("CertType.getCertType", loginId);
	}
}
