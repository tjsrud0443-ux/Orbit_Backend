package com.study.app.domains.aiChat;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class AiChatController {

	@Autowired
	private AiChatService aiServ;

	@GetMapping("/message")
	public ResponseEntity<Map<String, Object>> AiMessage(@RequestAttribute String loginId, 
			@RequestParam Long chat_seq, @RequestParam String role, @RequestParam String content) {
		Map<String, Object> aiResponse = aiServ.getRagResponse(loginId, chat_seq, role, content);
		return ResponseEntity.ok(aiResponse);
	}
}
