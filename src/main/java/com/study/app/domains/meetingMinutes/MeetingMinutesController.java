package com.study.app.domains.meetingMinutes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/minutes")
public class MeetingMinutesController {

	@Autowired
	private MeetingMinutesService minutesServ;

	@PostMapping
	public ResponseEntity<Void> insertMinutes(@RequestAttribute String loginId, @RequestBody MeetingMinutesDTO dto){
		dto.setUsers_id(loginId);
		minutesServ.insertMinutes(dto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/minutesList")
	public ResponseEntity<List<MeetingMinutesDTO>> getMinutesList(){
		List<MeetingMinutesDTO> list = minutesServ.getMinutesList();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/detail/{minute_seq}")
	public ResponseEntity<MeetingMinutesDTO> getMinutesDetail(@PathVariable Long minute_seq){
		MeetingMinutesDTO list = minutesServ.getMinutesDetail(minute_seq);
		return ResponseEntity.ok(list);
	}
	
	@DeleteMapping("/{minute_seq}")
	public ResponseEntity<Void> deleteMinutesAll(@PathVariable Long minute_seq){
		minutesServ.deleteMinutesAll(minute_seq);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateMinutesAll(@RequestBody MeetingMinutesDTO dto) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 1. 서비스단의 수정 로직 호출 (우리가 만든 대포 발사!)
        	minutesServ.updateMinutesAll(dto);         
            // 2. 성공 시 프론트엔드로 보낼 응답 데이터 세팅
            response.put("status", "success");
            response.put("message", "회의록이 성공적으로 수정되었습니다.");
            
            return ResponseEntity.ok(response); // 200 OK 상태코드와 함께 반환
            
        } catch (Exception e) {
            // 3. 예외 발생 시 에러 메시지 반환
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "회의록 수정 중 오류가 발생했습니다: " + e.getMessage());
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 에러 반환
        }
    }
}
