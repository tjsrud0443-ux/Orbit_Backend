package com.study.app.domains.pageInfo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pageInfo")
public class PageInfoController {
	
	@Autowired
	private PageInfoService pageServ;
	
	@GetMapping("getPageInfoList")
	public ResponseEntity<Map<String, Object>> getPageInfoList() {
		return ResponseEntity.ok(pageServ.getPageInfoList());
	}
}
