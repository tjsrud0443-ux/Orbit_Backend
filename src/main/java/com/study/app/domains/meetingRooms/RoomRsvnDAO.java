package com.study.app.domains.meetingRooms;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRsvnDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<Long> selectRsvnSeqByRoomSeq(Long room_seq) {
		return mybatis.selectOne("RoomRsvn.selectRsvnSeqByRoomSeq", room_seq);
	}
	
	public void deleteRsvnMember(List<Long> rsvnList) {
		mybatis.delete("RoomRsvn.deleteRsvnMember", rsvnList);
	}
	
	public void deleteRsvn(Long room_seq) {
		mybatis.delete("RoomRsvn.deleteRsvn", room_seq);
	}
}
