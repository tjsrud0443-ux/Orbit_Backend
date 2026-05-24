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
		
		Map<Long, GroupTreeDTO> nodeMap = new HashMap<>();

		for(GroupTreeDTO node : groupTree) {
			node.setChildren(new ArrayList<>());
			node.setMembers(new ArrayList<>());
			
			List<GroupMemberDTO> members = dao.selectMembers(node.getDeptSeq());
			node.setMembers(members);
			nodeMap.put(node.getDeptSeq(), node);
		}

		GroupTreeDTO root = null;

		for(GroupTreeDTO node : groupTree) {
			Long parentSeq = node.getParentDeptSeq();

			if(parentSeq == null) {
				root = node;
			}else {
				GroupTreeDTO parent = nodeMap.get(parentSeq);

				if(parent != null) {
					parent.getChildren().add(node);
				}
			}
		}
		Map<String, Object> result = new HashMap<>();
		result.put("root", root);
		result.put("nodeMap", nodeMap);
		result.put("users", groupList);
		
		return result;
	}
}