package com.study.app.domains.companyInfo;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyInfoDAO {

	@Autowired
	private SqlSessionTemplate batis;
	
	public CompanyInfoDTO getCompanyInfo() {
		return batis.selectOne("CompanyInfo.getCompanyInfo");
	}
	
	public void insertCompanyInfo(CompanyInfoDTO dto) {
		batis.insert("CompanyInfo.insertCompanyInfo", dto);
	}
	
	public void updateCompanyInfo(Map<String, Object> params) {
		batis.update("CompanyInfo.updateCompanyInfo", params);
	}
	
}
