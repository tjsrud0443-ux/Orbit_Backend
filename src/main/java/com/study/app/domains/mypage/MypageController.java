package com.study.app.domains.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.domains.aiChat.AiUnansweredQuestionsDTO;
import com.study.app.domains.annualLeave.AnnualLeaveDTO;
import com.study.app.domains.meetingRooms.RoomRsvnDTO;

@RestController
@RequestMapping("/mypage")
public class MypageController {

	@Autowired
	private MypageService mypageServ;
	
	@GetMapping("/annualSummary")
	public ResponseEntity<AnnualLeaveDTO> getAnnualLeaveSummary(@RequestAttribute String loginId){
		AnnualLeaveDTO annualSummary = mypageServ.getAnnualLeaveSummary(loginId);
		return ResponseEntity.ok(annualSummary);
	}
	
	@GetMapping("/myQuestions")
	public ResponseEntity<List<AiUnansweredQuestionsDTO>> myQuestions(@RequestAttribute String loginId) {
		return ResponseEntity.ok(mypageServ.myQuestions(loginId));
	}
	
	@DeleteMapping("/deleteMyQuestions/{question_seq}")
	public ResponseEntity<Void> deleteMyQuestions(@PathVariable Long question_seq) {
		mypageServ.deleteMyQuestions(question_seq);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("getAllMyMeetRsvn")
	public ResponseEntity<List<RoomRsvnDTO>> getAllMyMeetRsvn(@RequestAttribute String loginId){
		List<RoomRsvnDTO> list = mypageServ.getAllMyMeetRsvn(loginId);
		return ResponseEntity.ok(list);
	}
}
