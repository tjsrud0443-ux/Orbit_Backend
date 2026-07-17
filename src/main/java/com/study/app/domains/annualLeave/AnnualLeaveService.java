package com.study.app.domains.annualLeave;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnualLeaveService {

	@Autowired
	private AnnualLeaveDAO dao;

	@Transactional
	public void updateAnnualLeave() {

		dao.insertMissingAnnualLeave();
		List<AnnualLeaveUpdateDTO> users = dao.findAnnualLeaveTargetUsers();
		LocalDate today = LocalDate.now();

		for (AnnualLeaveUpdateDTO dto : users) {
			if (dto.getHire_date() == null || dto.getHire_date().isBlank()) {
	            continue;
	        }

			LocalDate hireDate = LocalDate.parse(dto.getHire_date());
			double totalDays = calculateAnnualLeaveDays(hireDate, today);
			double usedDays = dto.getUsed_days();
			double remainingDays = Math.max(totalDays - usedDays, 0);

			dao.updateAnnualLeave(dto.getLeave_seq(),totalDays,remainingDays);
		}
	}
	
	private double calculateAnnualLeaveDays(LocalDate hireDate, LocalDate today) {
		if(hireDate == null || hireDate.isAfter(today)) {
			return 0;
		}
		
		long completedMonths =
	            ChronoUnit.MONTHS.between(hireDate, today);

	    if (completedMonths < 12) {
	        return Math.min(completedMonths, 11);
	    }

	    long completedYears =
	            ChronoUnit.YEARS.between(hireDate, today);

	    long additionalDays =
	            Math.max((completedYears - 1) / 2, 0);

	    return Math.min(15 + additionalDays, 25);
	}
	
	//연차 관리
	public Map<String, Object> getAllLeaveList(String keyword, Long cPage) {
	    long page = (cPage == null || cPage < 1) ? 1 : cPage;
	    int start = (int) ((page - 1) * 10) + 1;
	    int end = (int) (page * 10);

	    Map<String, Object> params = new HashMap<>();
	    params.put("keyword", keyword);
	    params.put("start", start);
	    params.put("end", end);

	    List<AdminLeaveDTO> list = dao.getAllLeaveList(params);
	    int totalCount = dao.getLeaveCount(params);

	    Map<String, Object> result = new HashMap<>();
	    result.put("list", list);
	    result.put("totalCount", totalCount);
	    return result;
	}
	
	public Map<String, Object> updateAdminLeave(AdminLeaveDTO dto) {
	    // 1. 현재 값 DB에서 조회
		  AdminLeaveDTO current = dao.getLeaveBySeq(dto.getLeave_seq());
		    if (current == null) {
		        throw new IllegalArgumentException("해당 연차 정보를 찾을 수 없습니다. leave_seq=" + dto.getLeave_seq());
		    }

	    double currentTotal = current.getTotal_days() != null ? current.getTotal_days() : 0;
	    double usedDays = current.getUsed_days() != null ? current.getUsed_days() : 0;
	    
	    // 2. 가감 적용
	    double delta = dto.getDelta_days() != null ? dto.getDelta_days() : 0;
	    double newTotal = Math.max(currentTotal + delta, 0); // 음수 방지
	    double remainingDays = newTotal - usedDays;
	    
	    // 3. DB 반영
	    Map<String, Object> params = new HashMap<>();
	    params.put("leave_seq", dto.getLeave_seq());
	    params.put("total_days", newTotal);
	    params.put("remaining_days", remainingDays);

	    int result = dao.updateAdminLeave(params);

	    // 4. 프론트에 최종값 응답
	    Map<String, Object> response = new HashMap<>();
	    response.put("total_days", newTotal);
	    response.put("used_days", usedDays);
	    response.put("remaining_days", remainingDays);
	    
	    return response;
	}
}
