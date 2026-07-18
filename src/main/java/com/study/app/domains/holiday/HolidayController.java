package com.study.app.domains.holiday;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
