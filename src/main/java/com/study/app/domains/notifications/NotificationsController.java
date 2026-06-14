package com.study.app.domains.notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/noti")
public class NotificationsController {

	@Autowired
	private NotificationsService notiServ;
	
	@GetMapping("/getMyNotiList")
	public ResponseEntity<List<NotificationsDTO>> getMyNotiListByLoginId(@RequestAttribute String loginId) {
		return ResponseEntity.ok(notiServ.getMyNotiListByLoginId(loginId));
	}
	
	@GetMapping("/getNotiProjectSeq/{ref_seq}")
	public ResponseEntity<Long> getNotiProjectSeq(@PathVariable Long ref_seq) {
		return ResponseEntity.ok(notiServ.getNotiProjectSeq(ref_seq));
	}
	
	@GetMapping("/getNotiDocType/{ref_seq}")
	public ResponseEntity<String> getNotiDocType(@PathVariable Long ref_seq) {
		return ResponseEntity.ok(notiServ.getNotiDocType(ref_seq));
	}
	
}
