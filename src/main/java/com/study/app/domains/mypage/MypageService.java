package com.study.app.domains.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.aiChat.AiChatDAO;
import com.study.app.domains.aiChat.AiUnansweredQuestionsDTO;
import com.study.app.domains.annualLeave.AnnualLeaveDAO;
import com.study.app.domains.annualLeave.AnnualLeaveDTO;
import com.study.app.domains.meetingRooms.MeetingRoomsDTO;
import com.study.app.domains.meetingRooms.MeetingRoomsService;
import com.study.app.domains.meetingRooms.OccupiedTimeDTO;
import com.study.app.domains.meetingRooms.RoomRsvnDTO;

@Service
public class MypageService {
	
	@Autowired
	private AnnualLeaveDAO annualDAO;
	@Autowired
	private AiChatDAO aiChatDao;
	@Autowired
	private MeetingRoomsService roomServ;
	
	public AnnualLeaveDTO getAnnualLeaveSummary(String loginId) {
		return annualDAO.getAnnualLeaveSummary(loginId);
	}
	
	public List<AiUnansweredQuestionsDTO> myQuestions(String loginId) {
		return aiChatDao.myQuestions(loginId);
	}
	
	public void deleteMyQuestions(Long question_seq) {
		aiChatDao.deleteMyQuestions(question_seq);
	}
	
	public List<RoomRsvnDTO> getAllMyMeetRsvn(String loginId) {
		return roomServ.getAllMyMeetRsvn(loginId);
	}
	
	public List<RoomRsvnDTO> getMeetRsvnDetail(Long rsvn_seq) {
		return roomServ.getMeetRsvnDetail(rsvn_seq);
	}
	
	public List<MeetingRoomsDTO> getAllRooms() {
		return roomServ.getAllRooms();
	}
	
	public List<OccupiedTimeDTO> getOccupiedTimes(Long room_seq, String date, Long rsvn_seq) {
		return roomServ.getOccupiedTimes(room_seq, date, rsvn_seq);
	}
	
	public void updateMeetRsvn(RoomRsvnDTO dto) {
		roomServ.updateMeetRsvn(dto);
	}
	
	public void cancelMeetRsvn(Long rsvn_seq) {
		roomServ.cancelMeetRsvn(rsvn_seq);
	}
}