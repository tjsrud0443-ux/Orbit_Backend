package com.study.app.domains.aiChat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class AiChatController {

	@Autowired
	private AiChatService aiServ;
	
	@GetMapping("/message")
	public ResponseEntity<String> AiMessage(@RequestParam String role, @RequestParam String content) {
		System.out.println("role -> " + role);
		System.out.println("content -> " + content);
		
		String aiResponse = aiServ.getRagResponse(role, content);
		return ResponseEntity.ok(aiResponse);
	}
}
