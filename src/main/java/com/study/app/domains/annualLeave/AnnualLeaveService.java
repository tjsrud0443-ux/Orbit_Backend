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
			double baseDays = calculateAnnualLeaveDays(hireDate, today);
			double adminDays = dto.getAdmin_days();
			double usedDays = dto.getUsed_days();

			double totalDays = Math.max(baseDays + adminDays, 0);
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
	    // 현재 값 DB에서 조회, 최신 상태 확인용
		  AdminLeaveDTO current = dao.getLeaveBySeq(dto.getLeave_seq());
		  //조회 결과가 없으면(잘못된 leave_seq) 예외 발생시켜서 중단
		    if (current == null) {
		        throw new IllegalArgumentException("해당 연차 정보를 찾을 수 없습니다. leave_seq=" + dto.getLeave_seq());
		    }
		  //현재값들을 꺼내옴 (DB값이 null이면 0으로 처리)
	    double currentTotal = current.getTotal_days() != null ? current.getTotal_days() : 0;
	    double currentAdmin = current.getAdmin_days() != null ? current.getAdmin_days() : 0;
	    double usedDays = current.getUsed_days() != null ? current.getUsed_days() : 0;
	    //기존 total에서 기존 admin 조정분을 빼면 자동계산분만 남음
	    double autoBase = currentTotal - currentAdmin;
	    
	    // 관리자가 부여/회수하려는 증감값 (+면 부여, -면 회수)
	    //    null이면 0으로 처리해서 아무 변화 없게 함
	    double delta = dto.getDelta_days() != null ? dto.getDelta_days() : 0;
	    //관리자 조정 누적값(admin_days)에 이번 delta를 더해서 새 admin_days 계산
	    double newAdmin = currentAdmin + delta;
	    //새로운 총 연차 = 자동계산분(autoBase) + 새 admin 조정분(newAdmin)
	    double newTotal = Math.max(autoBase + newAdmin, 0);
	    
	    // 회수 후 총 연차가 잔여 연차보다 적어지면 예외 발생
	    if (newTotal < usedDays) {
	        double maxRevocable = currentTotal - usedDays; // = 현재 잔여연차
	        throw new IllegalArgumentException(
	            "잔여 연차(" + maxRevocable + "일)보다 많은 연차를 회수할 수 없습니다. " +
	            "요청한 회수 일수: " + Math.abs(delta) + "일"
	        );
	    }
	    
	    //잔여 연차 = 새 총 연차 - 이미 사용한 연차
	    double remainingDays = newTotal - usedDays;
	    
	    // DB에 update 반영할것들
	    Map<String, Object> params = new HashMap<>();
	    params.put("leave_seq", dto.getLeave_seq());
	    params.put("total_days", newTotal);
	    params.put("admin_days", newAdmin);
	    params.put("remaining_days", remainingDays);

	    int result = dao.updateAdminLeave(params);

	    // 프론트에 반환할 최종값 
	    Map<String, Object> response = new HashMap<>();
	    response.put("total_days", newTotal);
	    //used_days는 이번 로직에서 변경되지 않으므로 조회했던 값 그대로 반환
	    response.put("used_days", usedDays);
	    response.put("remaining_days", remainingDays);
	    response.put("admin_days", newAdmin);
	    
	    return response;
	}
}
