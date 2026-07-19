package com.study.app.domains.departments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentsService {

	@Autowired
	private DepartmentsDAO dao;

	public Map<String, Object> getGroup() {

		List<GroupTreeDTO> groupTree = dao.selectGroupTree();
	    List<GroupListDTO> groupList = dao.selectGroupList();
	    List<GroupMemberDTO> allMembers = dao.selectAllMembers();

	    Map<Long, GroupTreeDTO> nodeMap = new HashMap<>();

	    for (GroupTreeDTO node : groupTree) {
	        node.setChildren(new ArrayList<>());
	        node.setMembers(new ArrayList<>());
	        nodeMap.put(node.getDeptSeq(), node);
	    }

	    for (GroupMemberDTO member : allMembers) {
	        GroupTreeDTO deptNode = nodeMap.get(member.getDeptSeq());

	        if (deptNode != null) {
	            deptNode.getMembers().add(member);
	        }
	    }

	    GroupTreeDTO root = null;
	    
	    for (GroupTreeDTO node : groupTree) {
	        Long parentSeq = node.getParentDeptSeq();

	        if (parentSeq == null) {
	        	if(root != null) {
	        		throw new IllegalStateException("최상위 부서가 여러 개 존재합니다.");
	        	}
	            root = node;
	            continue;
	        }

	        GroupTreeDTO parent = nodeMap.get(parentSeq);

	        if (parent != null) {
	            parent.getChildren().add(node);
	        }
	    }
	    
	    if(root == null) {
	    	throw new IllegalStateException("최상위 부서가 존재하지 않습니다.");
	    }

	    Map<String, Object> result = new HashMap<>();
	    result.put("root", root);
	    result.put("nodeMap", nodeMap);
	    result.put("users", groupList);

	    return result;
	}
}