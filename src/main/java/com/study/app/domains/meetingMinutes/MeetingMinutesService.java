package com.study.app.domains.meetingMinutes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public void deleteMinutesAll(Long minute_seq) {
		// 1. 자식 테이블(참석자) 데이터 먼저 삭제
		minutesAttendeesDAO.deleteMinutesAttendees(minute_seq);
	    
	    // 2. 부모 테이블(회의록) 데이터 최종 삭제
	    minutesDAO.deleteMinutes(minute_seq);
	}
	
	//전부 성공하면 commit, 하나라도 실패하면 전부 rollback
	@Transactional 
	public void updateMinutesAll(MeetingMinutesDTO dto) {
	    // 1. 회의록 본문 내용 수정 (제목, 내용 등 수정)
	    minutesDAO.updateMinutes(dto); 
	    
	    // 2. [재사용 1] 기존 참석자 싹 지우기
	    minutesAttendeesDAO.deleteMinutesAttendees(dto.getMinute_seq()); 
	    
	    // 3. [재사용 2] 처음 등록할 때 쓰던 insert 쿼리를 그대로 재사용해서 새로 넣기
	    if (dto.getAttendees() != null) {
	        for (MinutesAttendeesDTO emp : dto.getAttendees()) {
	        	// 꺼내온 emp에 현재 수정 중인 회의록 번호(minute_seq)를 심어줍니다.
	            emp.setMinute_seq(dto.getMinute_seq());
	            
	            //  DTO 객체인 emp를 그대로 매개변수로
	            minutesAttendeesDAO.insertMinutesAttendees(emp);
	        }
	    }
	}
}
