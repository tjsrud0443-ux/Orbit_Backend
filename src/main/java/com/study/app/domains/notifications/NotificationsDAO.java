package com.study.app.domains.notifications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class NotificationsDAO {

	@Autowired
	private SqlSessionTemplate batis;
	
	public void insertNoti(NotificationsDTO dto) {
		batis.insert("Notifications.insertNoti", dto);
	}
	
	public void deleteProjectNotiBySeq(Long ref_seq) {
		batis.delete("Notifications.deleteProjectNotiBySeq", ref_seq);
	}
	
	public void deleteNotiByRsvnList(List<Long> rsvnList) {
		batis.delete("Notifications.deleteNotiByRsvnList", rsvnList);
	}
	
	public void deleteNotiBySeqAndId(Long rsvn_seq, String users_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("ref_seq", rsvn_seq);
		params.put("users_id", users_id);
		batis.delete("Notifications.deleteNotiBySeqAndId", params);
	}
	
	public void deleteMeetingNotiBySeq(Long ref_seq) {
		batis.delete("Notifications.deleteMeetingNotiBySeq", ref_seq);
	}
	
	public void deleteApprovalNotiBySeq(Long ref_seq) {
		batis.delete("Notifications.deleteApprovalNotiBySeq", ref_seq);
	}
	
	public List<NotificationsDTO> getMyNotiListByLoginId(String loginId) {
		return batis.selectList("Notifications.getMyNotiListByLoginId", loginId);
	}
	
	public void updateReadNoti(Long noti_seq) {
		batis.update("Notifications.updateReadNoti", noti_seq);
	}
}
