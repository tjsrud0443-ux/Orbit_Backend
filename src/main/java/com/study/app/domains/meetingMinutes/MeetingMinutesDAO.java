package com.study.app.domains.meetingMinutes;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MeetingMinutesDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int insertMinutes(MeetingMinutesDTO dto) {
		return mybatis.insert("MeetingMinutes.insertMinutes",dto);
	}
	
	public List<MeetingMinutesDTO> getMinutesList() {
		return mybatis.selectList("MeetingMinutes.getMinutesList");
	}
	
	public MeetingMinutesDTO getMinutesDetail(Long minute_seq) {
		return mybatis.selectOne("MeetingMinutes.getMinutesDetail",minute_seq);
	}
	
	public int deleteMinutes(Long minute_seq) {
		return mybatis.delete("MeetingMinutes.deleteMinutes",minute_seq);
	}
	
	public int updateMinutes(MeetingMinutesDTO dto) {
		return mybatis.delete("MeetingMinutes.updateMinutes",dto);
	}
	
	public List<Long> meetingSeqs(String loginId) {
		return mybatis.selectList("MeetingMinutes.meetingSeqs", loginId);
	}
	
	public List<Long> meetingSeqsByDate(Map<String, Object> params) {
		return mybatis.selectList("MeetingMinutes.meetingSeqsByDate", params);
	}
}
