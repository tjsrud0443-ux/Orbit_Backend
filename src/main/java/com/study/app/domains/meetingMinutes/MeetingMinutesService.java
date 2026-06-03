package com.study.app.domains.meetingMinutes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingMinutesService {
	@Autowired
	private MeetingMinutesDAO minutesDAO;
	
	@Autowired
	private MinutesAttendeesDAO minutesAttendeesDAO;
	
	public int insertMinutes(MeetingMinutesDTO dto) {
	    int result = minutesDAO.insertMinutes(dto);  // insert 후 dto.minuteSeq에 자동으로 생성된 seq 담김
	    
	    if (dto.getAttendees() != null) {
	        for (MinutesAttendeesDTO attendee : dto.getAttendees()) {
	        	 attendee.setMinute_seq(dto.getMinute_seq()); 
	        	 minutesAttendeesDAO.insertMinutesAttendees(attendee);            
	        }
	    }
	    return result;		
	}
	
	public List<MeetingMinutesDTO> getMinutesList() {
		return minutesDAO.getMinutesList();
	}
	
	public MeetingMinutesDTO getMinutesDetail(Long minute_seq) {
		return minutesDAO.getMinutesDetail(minute_seq);
	}
}
