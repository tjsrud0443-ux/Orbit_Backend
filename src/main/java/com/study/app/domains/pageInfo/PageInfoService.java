package com.study.app.domains.pageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageInfoService {
	
	@Autowired
	private PageInfoDAO dao;
	
	public Map<String, Object> getPageInfoList() {
		List<String> categories = dao.getPageCategories();
		List<PageInfoDTO> pages = dao.getPageInfoList();
		
		Map<String, Object> result = new HashMap<>();
		result.put("categories", categories);
		result.put("pages", pages);
		
		return result;
	}
	
	public void updatePageInfo(Long page_seq, PageInfoDTO dto) {
		dto.setPage_seq(page_seq);
		dao.updatePageInfo(dto);
	}
	
	public void updateCategory(String oldCategoryName, String newCategoryName) {
		dao.updateCategory(oldCategoryName, newCategoryName);
	}
}
