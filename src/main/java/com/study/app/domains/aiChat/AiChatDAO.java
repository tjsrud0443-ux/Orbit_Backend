package com.study.app.domains.aiChat;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AiChatDAO {

	@Autowired
	private SqlSessionTemplate batis;
	
	public void insertAiChat(AiChatDTO dto) {
		batis.insert("AiChat.insertAiChat", dto);
	}
	
	public void updateAiChatUpdated_at(Long chat_seq) {
		batis.update("AiChat.updateAiChatUpdated_at", chat_seq);
	}
	
	public void insertMessage(AiMessagesDTO dto) {
		batis.insert("AiChat.insertMessage", dto);
	}
}
