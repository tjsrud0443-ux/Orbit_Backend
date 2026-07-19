package com.study.app.domains.holiday;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holidays")
public class HolidayController {
	@Autowired
    private HolidayService holidayServ;
	
	@GetMapping
    public ResponseEntity<List<HolidayDTO>> getHolidays(@RequestParam int year) {
        return ResponseEntity.ok(holidayServ.getHolidays(year));
    }
	
	/*
     관리자 전용 강제 재동기화 엔드포인트
     */
    @PostMapping("/{year}/resync")
    public ResponseEntity<?> resyncHolidays(@PathVariable int year) {
        List<HolidayDTO> result = holidayServ.resyncHolidays(year);
        return ResponseEntity.ok(result);
    }
}
