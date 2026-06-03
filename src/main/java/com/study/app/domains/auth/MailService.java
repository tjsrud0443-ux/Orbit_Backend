package com.study.app.domains.auth;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.study.app.domains.users.UsersDAO;

@Service
@EnableScheduling
public class MailService {
	
	@Autowired
	private UsersDAO userDao;
	
	private final JavaMailSender mailSender;
    // 서버 메모리에 이메일과 인증정보를 저장하는 맵
    private final Map<String, VerificationInfo> verificationStorage = new ConcurrentHashMap<>();
    private final Map<String, String> passwordResetTokens = new ConcurrentHashMap<>();
    
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    // 아이디 찾기용
    public boolean isExistForId(String name, String email) {
        boolean isExist = userDao.isExistForId(name, email);

        if (!isExist) {
        	return false;
        }
        sendMail(email);
        return true;
    }

    // 비밀번호 찾기용
    public boolean isExistForPw(String name, String id, String email) {
        boolean isExist = userDao.isExistForPw(name, id, email);

        if (!isExist) {
            return false;
        }
        sendMail(email);
        return true;
    }

    // 실제 난수 생성 및 인증번호 메일 발송
    private void sendMail(String email) {
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(3);
        verificationStorage.put(email, new VerificationInfo(code, expiresAt));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[Orbit] 회원 인증 번호 안내");
        message.setText("안녕하세요. 요청하신 인증번호는 [" + code + "] 입니다. 3분 이내에 입력해주세요.");
        mailSender.send(message);
    }

    // 아이디- 인증번호 검증 후 맞으면 ID 반환
    public String findId(String email, String code) {
        VerificationInfo info = verificationStorage.get(email);
        if (info == null || LocalDateTime.now().isAfter(info.getExpiresAt())) {
            if (info != null) verificationStorage.remove(email);
            return null;
        }
        
        if (info.getCode().equals(code)) {
            verificationStorage.remove(email);
            
            return userDao.findIdByEmail(email);
        }
        return null;
    }

    // 비밀번호- 인증번호 검증 후 맞으면 변경 토큰 발급
    public String verifyForPw(String email, String code) {
        VerificationInfo info = verificationStorage.get(email);
        if (info == null || LocalDateTime.now().isAfter(info.getExpiresAt())) {
            if (info != null) verificationStorage.remove(email);
            return null;
        }
        
        if (info.getCode().equals(code)) {
            verificationStorage.remove(email);
            
            String resetToken = UUID.randomUUID().toString();
            passwordResetTokens.put(email, resetToken);
            return resetToken;
        }
        return null;
    }

    // 비밀번호 변경
    public boolean changePassword(String email, String newPassword, String token) {
        String savedToken = passwordResetTokens.get(email);
        if (savedToken != null && savedToken.equals(token)) {
            passwordResetTokens.remove(email);
            // TODO: 실제 DB 패스워드 업데이트 처리 기입
            System.out.println("성공: " + email + "의 비밀번호가 변경됨.");
            return true;
        }
        return false;
    }

    @Scheduled(fixedDelay = 60000)
    public void clearExpiredCodes() {
        LocalDateTime now = LocalDateTime.now();
        verificationStorage.entrySet().removeIf(entry -> now.isAfter(entry.getValue().getExpiresAt()));
    }
}
