package com.study.app.domains.holiday;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HolidayDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 해당 연도 데이터가 DB에 몇 건 있는지 확인
    public int countByYear(int year) {
        return mybatis.selectOne("Holiday.countByYear", year);
    }

    // 해당 연도의 공휴일 목록 조회
    public List<HolidayDTO> getByYear(int year) {
        return mybatis.selectList("Holiday.getByYear", year);
    }

    // 공휴일 한 건 저장
    public void insertHoliday(int year, HolidayDTO dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("year", year);
        params.put("locdate", dto.getLocdate());
        params.put("date_name", dto.getDate_name());
        params.put("is_holiday", dto.getIs_holiday());
        
        mybatis.insert("Holiday.insertHoliday", params);
    }
}
