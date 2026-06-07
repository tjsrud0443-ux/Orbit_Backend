package com.study.app.domains.meetingRooms;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.file.FileService;

@Service
public class MeetingRoomsService {
	
	@Autowired
	private MeetingRoomsDAO dao;
	@Autowired
	private FileService fileServ;
	
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
				String sysname = dao.selectOldSysname(dto.getRoom_seq());
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
}
