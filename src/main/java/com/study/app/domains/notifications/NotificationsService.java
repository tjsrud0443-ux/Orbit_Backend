package com.study.app.domains.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.study.app.domains.projects.ProjectMembersDTO;

@Service
public class NotificationsService {
	
	@Autowired
	private SimpMessagingTemplate stomp;
	
	@Autowired
	private NotificationsDAO notiDao;
	
	public void insertProjectNoti(NotificationsDTO dto) {
		String message = dto.getContent();
		notiDao.insertProjectNoti(dto);
		
		stomp.convertAndSend("/sub/notification/" + dto.getNoti_seq(), message);
	}
}
