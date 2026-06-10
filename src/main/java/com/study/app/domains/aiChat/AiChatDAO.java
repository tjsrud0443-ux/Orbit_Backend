package com.study.app.domains.aiChat;

import java.util.List;
import java.util.Map;

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
	
	public List<AiChatDTO> sideChatTitleList(String loginId) {
		return batis.selectList("AiChat.sideChatTitleList", loginId);
	}
	
	public List<AiMessagesDTO> detailChat(Long chat_seq) {
		return batis.selectList("AiChat.detailChat", chat_seq);
	}
	
	public void insertQuestion(Map<String, Object> params) {
		batis.insert("AiChat.insertQuestion", params);
	}
	
	public void deleteQuestions(Long chat_seq) {
		batis.delete("AiChat.deleteQuestions", chat_seq);
	}
	
	public void deleteAiMessages(Long chat_seq) {
		batis.delete("AiChat.deleteAiMessages", chat_seq);
	}
	
	public void deleteAiChat(Long chat_seq) {
		batis.delete("AiChat.deleteAiChat", chat_seq);
	}
	
	public List<AiUnansweredQuestionsDTO> myQuestions(String loginId) {
		return batis.selectList("AiChat.myQuestions", loginId);
	}
	
	public void deleteMyQuestions(Long question_seq) {
		batis.delete("AiChat.deleteMyQuestions", question_seq);
	}
	
	public AiMessagesDTO lastUserQuestion(Long chat_seq) {
		return batis.selectOne("AiChat.lastUserQuestion", chat_seq);
	}
	
//	public List<AiMessagesDTO> recentMessages(Long chat_seq) {
//		return batis.selectList("AiChat.recentMessages", chat_seq);
//	}
}
