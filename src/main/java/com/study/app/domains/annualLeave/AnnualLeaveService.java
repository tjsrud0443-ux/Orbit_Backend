package com.study.app.domains.annualLeave;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
}
