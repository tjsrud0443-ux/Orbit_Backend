package com.study.app.domains.signup;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.file.FileService;

@Service
public class SignupService {
	
	@Autowired
	private SignupDAO dao;
	@Autowired
	private FileService fileServ;
	
	@Transactional
	public void signupRequest(SignupDTO dto, MultipartFile profile) throws Exception{
		
		if (profile != null && !profile.isEmpty()) {

            Map<String, String> fileInfo = fileServ.upload(profile);

            dto.setOriname(fileInfo.get("oriname"));
            dto.setSysname(fileInfo.get("sysname"));
		}
		dao.signupRequest(dto);
	}
}
