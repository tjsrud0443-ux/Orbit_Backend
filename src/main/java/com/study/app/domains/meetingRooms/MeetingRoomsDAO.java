package com.study.app.domains.meetingRooms;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MeetingRoomsDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<MeetingRoomsDTO> getAllRooms(){
		return mybatis.selectList("MeetingRooms.getAllRooms");
	}
	
	public void addMeetingRoom(MeetingRoomsDTO dto) {
		mybatis.insert("MeetingRooms.addMeetingRoom", dto);
	}
	
	public String selectOldSysname(Long room_seq) {
		return mybatis.selectOne("MeetingRooms.selectOldSysname", room_seq);
	}
	
	public void editMeetingRoom(MeetingRoomsDTO dto) {
		mybatis.update("MeetingRooms.editMeetingRoom", dto);
	}
}
