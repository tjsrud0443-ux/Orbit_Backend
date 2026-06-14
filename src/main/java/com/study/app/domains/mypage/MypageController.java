package com.study.app.domains.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.domains.aiChat.AiUnansweredQuestionsDTO;
import com.study.app.domains.annualLeave.AnnualLeaveDTO;
import com.study.app.domains.meetingRooms.MeetingRoomsDTO;
import com.study.app.domains.meetingRooms.OccupiedTimeDTO;
import com.study.app.domains.meetingRooms.RoomRsvnDTO;
import com.study.app.domains.supplies.SupplyRequestDTO;

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
	
	@GetMapping("getMeetRsvnDetail/{rsvn_seq}")
	public ResponseEntity<List<RoomRsvnDTO>> getMeetRsvnDetail(@PathVariable Long rsvn_seq) {
		List<RoomRsvnDTO> list = mypageServ.getMeetRsvnDetail(rsvn_seq);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("getAllRooms")
	public ResponseEntity<List<MeetingRoomsDTO>> getAllRooms() {
		List<MeetingRoomsDTO> list = mypageServ.getAllRooms();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("getOccupiedTimes")
	public ResponseEntity<List<OccupiedTimeDTO>> getOccupiedTimes(@RequestParam Long room_seq,
														 @RequestParam String date,
														 @RequestParam Long rsvn_seq) {
		
		List<OccupiedTimeDTO> resp = mypageServ.getOccupiedTimes(room_seq, date, rsvn_seq);
		return ResponseEntity.ok(resp);
	}
	
	@PutMapping("updateMeetRsvn")
	public ResponseEntity<Void> updateMeetRsvn(@RequestBody RoomRsvnDTO dto) {
		mypageServ.updateMeetRsvn(dto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("cancelMeetRsvn/{rsvn_seq}")
	public ResponseEntity<Void> cancelMeetRsvn(@PathVariable Long rsvn_seq) {
		mypageServ.cancelMeetRsvn(rsvn_seq);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/getMySupplyRequest")
	public ResponseEntity<?> mySupplyRequest(@RequestAttribute String loginId) {
		try {
	        List<SupplyRequestDTO> list = mypageServ.mySupplyRequest(loginId);
	        return ResponseEntity.ok(list);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회 실패");
	    }
	}
	
	@DeleteMapping("/deleteMySupplyRequest/{req_seq}")
	public ResponseEntity<?> deleteMySupplyRequest(@PathVariable Long req_seq) {
	    try {
	        mypageServ.deleteMySupplyRequest(req_seq);
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
	    }
	}
}
