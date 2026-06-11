package com.study.app.domains.notifications;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class NotificationsDAO {

	@Autowired
	private SqlSessionTemplate batis;
	
	public void insertProjectNoti(NotificationsDTO dto) {
		batis.insert("Notifications.insertProjectNoti", dto);
	}
	

}
