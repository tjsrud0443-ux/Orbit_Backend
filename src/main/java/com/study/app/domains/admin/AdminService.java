package com.study.app.domains.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.departments.DepartmentsDAO;
import com.study.app.domains.departments.DepartmentsDTO;
import com.study.app.domains.rank.RankDAO;
import com.study.app.domains.rank.RankDTO;

@Service
public class AdminService {
	
	@Autowired
	private DepartmentsDAO departmentsDao;
	@Autowired
	private RankDAO rankDao;
	
	public Map<String, Object> getDeptAndRank() {
        Map<String, Object> result = new HashMap<>();
        
        List<DepartmentsDTO> deptList = departmentsDao.getDeptList();
        List<RankDTO> rankList = rankDao.getRankList();
        
        result.put("departments", deptList);
        result.put("rank", rankList);
        
        return result;
    }
}
