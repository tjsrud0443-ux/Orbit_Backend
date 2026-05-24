package com.study.app.domains.departments;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {

	@Autowired
	private DepartmentsService departmentsServ;
	
	@GetMapping("/group")
	public ResponseEntity<Map<String, Object>> getGroup() {
		return ResponseEntity.ok(departmentsServ.getGroup());
	}
}
