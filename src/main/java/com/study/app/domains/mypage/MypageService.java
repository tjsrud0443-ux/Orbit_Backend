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
import com.study.app.domains.supplies.SupplyDAO;
import com.study.app.domains.supplies.SupplyRequestDTO;
import com.study.app.domains.supplies.SupplyService;

@Service
public class MypageService {
	
	@Autowired
	private AnnualLeaveDAO annualDAO;
	@Autowired
	private AiChatDAO aiChatDao;
	@Autowired
	private MeetingRoomsService roomServ;	
	@Autowired
	private SupplyDAO supplyDAO;
	@Autowired
	private SupplyService supplyServ;
	
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
	
	// 비품 신청 내역
	public List<SupplyRequestDTO> mySupplyRequest(String loginId) {
	    return supplyDAO.mySupplyRequest(loginId);
	}
	
	public void deleteMySupplyRequest(Long req_seq) {
        supplyServ.deleteMySupplyRequest(req_seq); 
    }
}