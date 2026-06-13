package com.study.app.domains.overtimeRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.app.domains.attendance.AttendanceService;

@Service
public class OvertimeRequestService {
	
	@Autowired
	private OvertimeRequestDAO dao;
	@Autowired
	private AttendanceService attServ;
	
	public Map<String, Object> getAllOvertimeRQ(Long cPage, String status) {
		int recordCountPerPage = 10;

	    Long start = (cPage - 1) * recordCountPerPage + 1;
	    Long end = cPage * recordCountPerPage;

	    Map<String, Object> param = new HashMap<>();

	    param.put("start", start);
	    param.put("end", end);
	    param.put("status", status);

	    List<OvertimeRequestDTO> list = dao.getAllOvertimeRQ(param);

	    int totalCount = dao.getCount(status);

	    Map<String, Integer> tabCount = dao.getTabCount();

	    Map<String, Object> result = new HashMap<>();

	    result.put("list", list);
	    result.put("count", totalCount);
	    result.put("tabCount", tabCount);

	    return result;
    }
	
	@Transactional
	public void approveOvertime(Long overtime_seq, String loginId) {
		OvertimeRequestDTO info = dao.getOvertimeInfo(overtime_seq);
		attServ.updateOvertime(info);
		dao.approveOvertime(overtime_seq, loginId);
	}
	
	public void rejectOvertime(Long overtime_seq, String loginId) {
		dao.rejectOvertime(overtime_seq, loginId);
	}
}
