package com.study.app.domains.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.app.domains.aiChat.AiChatDAO;
import com.study.app.domains.aiChat.AiUnansweredQuestionsDTO;
import com.study.app.domains.annualLeave.AnnualLeaveDAO;
import com.study.app.domains.annualLeave.AnnualLeaveDTO;
import com.study.app.domains.meetingRooms.MeetingRoomsService;
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
	
}