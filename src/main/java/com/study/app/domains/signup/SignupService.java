package com.study.app.domains.signup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.commons.Aes256Util;
import com.study.app.commons.EncryptionUtils;
import com.study.app.commons.MaskingUtil;
import com.study.app.commons.Sha256Util;
import com.study.app.domains.file.FileService;

@Service
public class SignupService {
	
	@Autowired
	private SignupDAO dao;
	@Autowired
	private FileService fileServ;
	@Autowired
    private Aes256Util aes256Util;
	
	public boolean isExistId(String id) {
		return dao.isExistId(id) > 0;
	}
	
	public boolean isExistEmail(String email) {
		return dao.isExistEmail(email) > 0;
	}
	
	@Transactional
	public void signupRequest(SignupDTO dto, MultipartFile profile) {
		
		String originPw = dto.getPw();
		
		if(originPw != null && !originPw.isEmpty()) {
			String pw = EncryptionUtils.getSha512(originPw);
			dto.setPw(pw);
		}
		
		String ssn = dto.getSsn();
	    
	    if (ssn != null && !ssn.isEmpty()) {
	        // AES256 암호화
	        String ssn_enc = aes256Util.encrypt(ssn);
	        dto.setSsn_enc(ssn_enc);
	        
	        // SHA256 암호화
	        String ssn_hash = Sha256Util.encrypt(ssn);
	        dto.setSsn_hash(ssn_hash);
	        
	        // 마스킹(비식별화)
	        String ssn_masked = MaskingUtil.masking(ssn);
	        dto.setSsn_masked(ssn_masked);
	    }
		
		if (profile != null && !profile.isEmpty()) {
			try {
				Map<String, String> fileInfo = fileServ.upload(profile);

				dto.setOriname(fileInfo.get("oriname"));
				dto.setSysname(fileInfo.get("sysname"));
			}catch(Exception e) {
				throw new RuntimeException("파일 업로드 실패", e);
			}
            
		}
		dao.signupRequest(dto);
	}
	
	public Map<String, Object> getAllRequest(Long cPage, String status, String searchTerm) {
		int recordCountPerPage = 10;

	    Long start = (cPage - 1) * recordCountPerPage + 1;
	    Long end = cPage * recordCountPerPage;

	    Map<String, Object> param = new HashMap<>();

	    param.put("start", start);
	    param.put("end", end);
	    param.put("status", status);
	    param.put("searchTerm", searchTerm);

	    List<SignupDTO> list = dao.getAllRequest(param);

	    int totalCount = dao.getCount(status);

	    Map<String, Integer> tabCount = dao.getTabCount();

	    Map<String, Object> result = new HashMap<>();

	    result.put("list", list);
	    result.put("count", totalCount);
	    result.put("tabCount", tabCount);

	    return result;
    }
	
	public SignupDTO getUserInfo(Long signup_seq) {
		return dao.getUserInfo(signup_seq);
	}
	
	public void rejectSignup(Long signup_seq) {
		dao.rejectSignup(signup_seq);
	}
}
