package com.study.app.domains.meetingRooms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRsvnDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<Long> selectRsvnSeqByRoomSeq(Long room_seq) {
		return mybatis.selectList("RoomRsvn.selectRsvnSeqByRoomSeq", room_seq);
	}
	
	public void deleteRsvnMember(List<Long> rsvnList) {
		mybatis.delete("RoomRsvn.deleteRsvnMember", rsvnList);
	}
	
	public void deleteRsvn(Long room_seq) {
		mybatis.delete("RoomRsvn.deleteRsvn", room_seq);
	}
	
	public List<RoomRsvnDTO> getAllMyMeetRsvn(String users_id) {
		return mybatis.selectList("RoomRsvn.getAllMyMeetRsvn", users_id);
	}
	
	public void createReservation(RoomRsvnDTO dto) {
		mybatis.insert("RoomRsvn.createReservation", dto);
	}
	
	public void addRsvnMembers(RoomRsvnMemberDTO dto) {
		mybatis.insert("RoomRsvn.addRsvnMembers", dto);
	}
	
	public List<RoomRsvnDTO> getReservations(String date, Long room_seq) {
		Map<String, Object> params = new HashMap<>();
		params.put("date", date);
		params.put("room_seq", room_seq);
		return mybatis.selectList("RoomRsvn.getReservations", params);
	}
	
	public List<RoomRsvnDTO> getMeetRsvnDetail(Long rsvn_seq) {
		return mybatis.selectList("RoomRsvn.getMeetRsvnDetail", rsvn_seq);
	}
	
	public List<OccupiedTimeDTO> getOccupiedTimes(Long room_seq, String date, Long rsvn_seq) {
		Map<String, Object> params = new HashMap<>();
		params.put("room_seq", room_seq);
		params.put("date", date);
		params.put("rsvn_seq", rsvn_seq);
		return mybatis.selectList("RoomRsvn.getOccupiedTimes", params);
	}
	
	public void updateMeetRsvn(RoomRsvnDTO dto) {
		mybatis.update("RoomRsvn.updateMeetRsvn", dto);
	}
	
	public void removeRsvnMember(Long rsvn_seq, String users_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("rsvn_seq", rsvn_seq);
		params.put("users_id", users_id);
		mybatis.delete("RoomRsvn.removeRsvnMember", params);
	}
	
	public void insertAddMember(Long rsvn_seq, String users_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("rsvn_seq", rsvn_seq);
		params.put("users_id", users_id);
		mybatis.insert("RoomRsvn.insertAddMember", params);
	}
	
	public void deleteMeetMember(Long rsvn_seq) {
		mybatis.delete("RoomRsvn.deleteMeetMember", rsvn_seq);
	}
	
	public void deleteMeetRsvn(Long rsvn_seq) {
		mybatis.delete("RoomRsvn.deleteMeetRsvn", rsvn_seq);
	}
}
