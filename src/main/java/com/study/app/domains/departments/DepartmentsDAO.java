package com.study.app.domains.departments;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentsDAO {

	@Autowired
	private SqlSessionTemplate batis;

	public List<GroupTreeDTO> selectGroupTree() {
		return batis.selectList("Departments.selectGroupTree");
	}

	public List<GroupListDTO> selectGroupList() {
		return batis.selectList("Departments.selectGroupList");
	}
	
	public List<GroupMemberDTO> selectMembers(Long deptSeq) {
		return batis.selectList("Departments.selectMembers", deptSeq);
	}
	
	public List<DepartmentsDTO> getDeptList(){
		return batis.selectList("Departments.getDeptList");
	}

}