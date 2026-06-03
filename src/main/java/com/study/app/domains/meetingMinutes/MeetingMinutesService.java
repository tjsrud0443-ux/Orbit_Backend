package com.study.app.domains.meetingMinutes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingMinutesService {
	@Autowired
	private MeetingMinutesDAO minutesDAO;
	
	public int insertMinutes(MeetingMinutesDTO dto) {
		return minutesDAO.insertMinutes(dto);
	}
}
