package com.study.app.domains.schedules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.app.domains.meetingRooms.RoomRsvnDTO;
import com.study.app.domains.projects.ProjectsDTO;

@Repository
public class SchedulesDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertSchedules(SchedulesDTO dto) {
		return mybatis.insert("Schedules.insertSchedules",dto);
	}
	
	public List<SchedulesDTO> getSchedules(String usersId) {
	    return mybatis.selectList("Schedules.getSchedules", usersId);
	}
	
	public int deleteSchedules(Long schedule_seq) {
		return mybatis.delete("Schedules.deleteSchedules",schedule_seq);
	}
	
	public int updateSchedules(SchedulesDTO dto) {
		return mybatis.update("Schedules.updateSchedules", dto);
	}
	
	public void addMeetingSchedules(SchedulesDTO dto) {
		mybatis.insert("Schedules.addMeetingSchedules", dto);
	}
	
	public void deleteMeetingSchedule(Long ref_seq, String users_id) {
		Map<String, Object> params = new HashMap<>();
		params.put("ref_seq", ref_seq);
		params.put("users_id", users_id);
		mybatis.delete("Schedules.deleteMeetingSchedule", params);
	}
	
	public void insertMeetAddMember(SchedulesDTO dto) {
		mybatis.insert("Schedules.insertMeetAddMember", dto);
	}
	
	public void updateMeetSchedule(RoomRsvnDTO dto) {
		mybatis.update("Schedules.updateMeetSchedule", dto);
	}
	
	public void cancelMeetRsvn(Long ref_seq) {
		mybatis.delete("Schedules.cancelMeetRsvn", ref_seq);
	}
	
	public void insertProjectSchedule(SchedulesDTO dto) {
		mybatis.insert("Schedules.insertProjectSchedule", dto);
	}
	
	public void insertProjectMemberSchedule(SchedulesDTO dto) {
		mybatis.insert("Schedules.insertProjectSchedule", dto);
	}
	
	public void deleteProjectMemberSchedule(Long ref_seq) {
		mybatis.delete("Schedules.deleteProjectMemberSchedule", ref_seq);
	}
	
	public void deleteProjectScheduleBySeq(Long ref_seq) {
		mybatis.delete("Schedules.deleteProjectScheduleBySeq", ref_seq);
	}
	
}
