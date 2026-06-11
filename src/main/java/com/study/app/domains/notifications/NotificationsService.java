package com.study.app.domains.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationsService {
	
	@Autowired
	private SimpMessagingTemplate stomp;
	
	@Autowired
	private NotificationsDAO notiDao;
	
	public void insertProjectNoti(NotificationsDTO dto) {
		notiDao.insertProjectNoti(dto);
		
		stomp.convertAndSend("/sub/notification/" + dto.getUsers_id(), dto);
		System.out.println("/sub/notification/" + " " + dto.getUsers_id() + " " + dto);
	}
}
