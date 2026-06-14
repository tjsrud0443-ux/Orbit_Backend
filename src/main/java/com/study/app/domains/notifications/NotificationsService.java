package com.study.app.domains.notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.study.app.domains.approval.ApprovalDAO;
import com.study.app.domains.projects.KanbanTaskDAO;

@Service
public class NotificationsService {
	
	@Autowired
	private SimpMessagingTemplate stomp;
	
	@Autowired
	private NotificationsDAO notiDao;
	
	@Autowired
	private KanbanTaskDAO kanbanDao;
	
	@Autowired
	private ApprovalDAO appDao;
	
	public void insertNoti(NotificationsDTO dto) {
		notiDao.insertNoti(dto);
		stomp.convertAndSend("/sub/notification/" + dto.getUsers_id(), dto);
	}
	
	public void deleteNotiByRsvnList(List<Long> rsvnList) {
		notiDao.deleteNotiByRsvnList(rsvnList);
	}
	
	public void deleteProjectNotiBySeq(Long project_seq) {
		notiDao.deleteProjectNotiBySeq(project_seq);
	}
	
	public void deleteNotiBySeqAndId(Long rsvn_seq, String users_id) {
		notiDao.deleteNotiBySeqAndId(rsvn_seq, users_id);
	}
	
	public void deleteMeetingNotiBySeq(Long rsvn_seq) {
		notiDao.deleteMeetingNotiBySeq(rsvn_seq);
	}
	
	public void deleteApprovalNotiBySeq(Long doc_seq) {
		notiDao.deleteApprovalNotiBySeq(doc_seq);
	}
	
	public List<NotificationsDTO> getMyNotiListByLoginId(String loginId) {
		return notiDao.getMyNotiListByLoginId(loginId);
	}
	
	public Long getNotiProjectSeq(Long ref_seq) {
		return kanbanDao.getNotiProjectSeq(ref_seq);
	}
	
	public String getNotiDocType(Long ref_seq) {
		return appDao.getNotiDocType(ref_seq);
	}
	
	public void updateReadNoti(Long noti_seq) {
		notiDao.updateReadNoti(noti_seq);
	}
}
