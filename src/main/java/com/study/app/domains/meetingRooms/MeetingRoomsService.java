package com.study.app.domains.meetingRooms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.file.FileService;
import com.study.app.domains.schedules.SchedulesService;

@Service
public class MeetingRoomsService {
	
	@Autowired
	private MeetingRoomsDAO dao;
	@Autowired
	private RoomRsvnDAO rsvnDao;
	@Autowired
	private FileService fileServ;
	@Autowired
	private SchedulesService scheServ;
	
	public List<MeetingRoomsDTO> getAllRooms(){
		return dao.getAllRooms();
	}
	
	@Transactional
	public void addMeetingRoom(MeetingRoomsDTO dto, MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			try {
				Map<String, String> fileInfo = fileServ.upload(file);

				dto.setOriname(fileInfo.get("oriname"));
				dto.setSysname(fileInfo.get("sysname"));
			}catch(Exception e) {
				throw new RuntimeException("파일 업로드 실패", e);
			}
		}
		dao.addMeetingRoom(dto);
	}
	
	@Transactional
	public void editMeetingRoom(MeetingRoomsDTO dto, MultipartFile file) {
		if(file != null && !file.isEmpty()) {
			try {
				String sysname = dao.selectSysname(dto.getRoom_seq());
				fileServ.deleteFromGCS(sysname);
				Map<String, String> fileInfo = fileServ.upload(file);
				
				dto.setOriname(fileInfo.get("oriname"));
				dto.setSysname(fileInfo.get("sysname"));
			}catch(Exception e) {
				throw new RuntimeException("파일 업로드 실패", e);
			}
		}
		dao.editMeetingRoom(dto);
	}
	
	@Transactional
	public void deleteMeetingRoom(Long room_seq) {
		fileServ.deleteFromGCS(dao.selectSysname(room_seq));
		
		List<Long> rsvnList = rsvnDao.selectRsvnSeqByRoomSeq(room_seq);
		if (rsvnList != null && !rsvnList.isEmpty()) {
	        rsvnDao.deleteRsvnMember(rsvnList);
	    }
		rsvnDao.deleteRsvn(room_seq);
		
		dao.deleteMeetingRoom(room_seq);
	}
	
	public List<RoomRsvnDTO> getAllMyMeetRsvn(String loginId) {
		return rsvnDao.getAllMyMeetRsvn(loginId);
	}
	
	@Transactional
	public void createReservation(RoomRsvnDTO dto, String loginId) {
		dto.setUsers_id(loginId);
		rsvnDao.createReservation(dto);
		scheServ.addMeetingSchedules(dto, loginId);
		
		if (dto.getAttendees() != null && !dto.getAttendees().isEmpty()) {
			for (RoomRsvnMemberDTO member : dto.getAttendees()) {
				member.setRsvn_seq(dto.getRsvn_seq());
				rsvnDao.addRsvnMembers(member);
				scheServ.addMeetingSchedules(dto, member.getUsers_id());
			}
		}
	}
	
	public List<RoomRsvnDTO> getReservations(String date, Long room_seq) {
		return rsvnDao.getReservations(date, room_seq);
	}
	
	public List<RoomRsvnDTO> getMeetRsvnDetail(Long rsvn_seq) {
		return rsvnDao.getMeetRsvnDetail(rsvn_seq);
	}
	
	public List<OccupiedTimeDTO> getOccupiedTimes(Long room_seq, String date, Long rsvn_seq) {
		return rsvnDao.getOccupiedTimes(room_seq, date, rsvn_seq);
	}
	
	@Transactional
	public void updateMeetRsvn(RoomRsvnDTO dto) {
		rsvnDao.updateMeetRsvn(dto);
		
		if (dto.getRemovedAttendees() != null && !dto.getRemovedAttendees().isEmpty()) {
			for (RoomRsvnMemberDTO member : dto.getRemovedAttendees()) {
				scheServ.deleteSchedule(dto.getRsvn_seq(), member.getUsers_id());
				rsvnDao.removeRsvnMember(dto.getRsvn_seq(), member.getUsers_id());
			}
		}
		
		if (dto.getAddedAttendees() != null && !dto.getAddedAttendees().isEmpty()) {
			for (RoomRsvnMemberDTO member : dto.getAddedAttendees()) {
				rsvnDao.insertAddMember(dto.getRsvn_seq(), member.getUsers_id());
				scheServ.insertMeetAddMember(dto, member.getUsers_id());
			}
		}
		scheServ.updateMeetSchedule(dto);
	}
}
