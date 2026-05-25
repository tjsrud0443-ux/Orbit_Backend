package com.study.app.domains.signup;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.app.domains.users.UsersDTO;

@Repository
public class SignupDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public int isExistId(String id) {
		return mybatis.selectOne("Signup.isExistId", id);
	}
	
	public void signupRequest(SignupDTO dto) {
		mybatis.insert("Signup.signupRequest", dto);
	}
	
	public int countAllRequest() {
		return mybatis.selectOne("Signup.countAllRequest");
	}
	
	public List<SignupDTO> getAllRequest(Map<String, Object> param){
        return mybatis.selectList("Signup.getAllRequest", param);
    }

    public int getCount(String status){
        return mybatis.selectOne("Signup.getCount", status);
    }

    public Map<String, Integer> getTabCount(){
        return mybatis.selectOne("Signup.getTabCount");
    }
    
    public SignupDTO getUserInfo(Long signup_seq) {
		return mybatis.selectOne("Signup.getUserInfo", signup_seq);
	}
    
    public int updateStatusToApproved(Long signup_seq) {
    	return mybatis.update("Signup.updateStatusToApproved", signup_seq);
    }
    
    public int rejectSignup(Long signup_seq) {
    	return mybatis.update("Signup.rejectSignup", signup_seq);
    }
}
