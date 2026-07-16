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

		List<AnnualLeaveUpdateDTO> users = dao.findOneYearUsers();
		LocalDate today = LocalDate.now();

		for (AnnualLeaveUpdateDTO dto : users) {

			// String -> 날짜 형식으로 변환
			LocalDate hireDate = LocalDate.parse(dto.getHire_date());
			// 입사일로부터 몇 개월이 흘렀는지
			long months = ChronoUnit.MONTHS.between(hireDate,today);
			// 위 정보로 총 연차 계산 
			// -> 1년 미만은 근속년수 계산 -> 최대 11개 발생
			// -> 근속년수가 12개월인 직원은 15개 지급)
			double totalDays = Math.min(months, 11);

			double remainingDays = totalDays - dto.getUsed_days();

			if (remainingDays < 0) {
				remainingDays = 0;
			}

			dao.updateAnnualLeave(dto.getLeave_seq(),totalDays,remainingDays);
		}
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
	    System.out.println(">>> updated rows: " + result);

	    // 4. 프론트에 최종값 응답
	    Map<String, Object> response = new HashMap<>();
	    response.put("total_days", newTotal);
	    response.put("used_days", usedDays);
	    response.put("remaining_days", remainingDays);
	    
	    return response;
	}
}
