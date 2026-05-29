package com.study.app.domains.admin;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.app.domains.departments.DepartmentsDTO;

@Repository
public class AdminDAO {

	@Autowired
	private SqlSessionTemplate batis;
	
	public void addDept(DepartmentsDTO dto) {
		batis.insert("Admin.addDept", dto);
	}
	
	public void addTeam(DepartmentsDTO dto) {
		batis.insert("Admin.addTeam", dto);
	}
	
	public void delDept(Long dept_seq) {
		batis.delete("Admin.delDept", dept_seq);
	}
	
	public void updateDept(DepartmentsDTO dto) {
		batis.update("Admin.updateDept", dto);
	}
}
