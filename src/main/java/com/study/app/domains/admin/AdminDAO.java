package com.study.app.domains.admin;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.app.domains.aiChat.AiUnansweredQuestionsDTO;
import com.study.app.domains.departments.DepartmentsCountDTO;
import com.study.app.domains.departments.DepartmentsDTO;
import com.study.app.domains.departments.DeptLeaveDTO;

@Repository
public class AdminDAO {

	@Autowired
	private SqlSessionTemplate batis;
	
	public void addDept(DepartmentsDTO dto) {
		batis.insert("Admin.addDept", dto);
	}
	
	public void deleteDefaultApprovalLineByDeptSeq(Long dept_seq) {
		batis.delete("Admin.deleteDefaultApprovalLineByDeptSeq", dept_seq);
	}
	
	public int deleteDept(Long dept_seq) {
		return batis.delete("Admin.deleteDept", dept_seq);
	}
	
	public DepartmentsDTO findDeptBySeq(Long dept_seq) {
		return batis.selectOne("Admin.findDeptBySeq", dept_seq);
	}
	
	public int isHq(Long dept_seq) {
		return batis.selectOne("Admin.isHq", dept_seq);
	}
	
	public int countChildDepartments(Long dept_seq) {
		return batis.selectOne("Admin.countChildDepartments", dept_seq);
	}
	
	public int updateDept(DepartmentsDTO dto) {
		return batis.update("Admin.updateDept", dto);
	}
	
	public int allEmployeeCount() {
		return batis.selectOne("Admin.allEmployeeCount");
	}
	
	public int joinEmployeeCount() {
		return batis.selectOne("Admin.joinEmployeeCount");
	}
	
	public int resignEmployeeCount() {
		return batis.selectOne("Admin.resignEmployeeCount");
	}
	
	public int aiQuestionsCount() {
		return batis.selectOne("Admin.aiQuestionsCount");
	}
	
	public int supplyRequestCount() {
		return batis.selectOne("Admin.supplyRequestCount");
	}
	
	public List<DepartmentsCountDTO> deptEmployeeCount() {
		return batis.selectList("Admin.deptEmployeeCount");
	}
	
	public List<DeptLeaveDTO> getDeptLeave() {
		return batis.selectList("Admin.getDeptLeave");
	}
	
	public List<JoinResignDTO> joinResignCount() {
		return batis.selectList("Admin.joinResignCount");
	}
	
	public List<AiUnansweredQuestionsDTO> getAiQuestions() {
		return batis.selectList("Admin.getAiQuestions");
	}
	
	public List<AiUnansweredQuestionsDTO> myDeptQuestion(Map<String, Object> params) {
		return batis.selectList("Admin.myDeptQuestion", params);
	}
	
	public void insertUpdateAnswer(AiUnansweredQuestionsDTO dto) {
		batis.update("Admin.insertUpdateAnswer", dto);
	}
	
	public void deleteAnswer(Long question_seq) {
		batis.update("Admin.deleteAnswer", question_seq);
	}
	
	public AiQuestionCountDTO adminAiQuestionsData(Map<String, Object> params) {
		return batis.selectOne("Admin.adminAiQuestionsData", params);
	}
}
