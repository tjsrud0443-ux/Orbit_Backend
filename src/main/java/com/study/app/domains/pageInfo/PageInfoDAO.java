package com.study.app.domains.pageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PageInfoDAO {
	
	@Autowired
	private SqlSessionTemplate myBatis;
	
	public List<String> getPageCategories() {
		return myBatis.selectList("PageInfo.getPageCategories");
	}
	
	public List<PageInfoDTO> getPageInfoList() {
		return myBatis.selectList("PageInfo.getPageInfoList");
	}
	
	public void updatePageInfo(PageInfoDTO dto) {
		myBatis.update("PageInfo.updatePageInfo", dto);
	}
	
	public void updateCategory(String oldCategoryName, String newCategoryName) {
		Map<String, String> params = new HashMap<>();
		params.put("oldCategoryName", oldCategoryName);
		params.put("newCategoryName", newCategoryName);
		myBatis.update("PageInfo.updateCategory", params);
	}
}
