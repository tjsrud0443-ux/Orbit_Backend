package com.study.app.domains.auth;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.study.app.commons.EncryptionUtils;
import com.study.app.domains.users.UsersDAO;

import jakarta.mail.internet.MimeMessage;

@Service
@EnableScheduling
public class MailService {
	
	@Autowired
	private UsersDAO usersDao;
	
	@Value("${app.mail.from}")
    private String fromEmail;
	
	@Value("${spring.mail.password}")
	private String mailPw;
	
	@PostConstruct
    public void test() {
        System.out.println("MAIL PW LENGTH = " + mailPw.length());
    }
	
	private final JavaMailSender mailSender;
    // 서버 메모리에 이메일과 인증정보를 저장하는 맵
    private final Map<String, VerificationInfo> verificationStorage = new ConcurrentHashMap<>();
    private final Map<String, String> passwordResetTokens = new ConcurrentHashMap<>();
    
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    // 아이디 찾기용
    public boolean isExistForId(String name, String email) {
    	if (usersDao.isExistForId(name, email) == 0) {
            return false; 
        }
        sendMail(email);
        return true;
    }

    // 비밀번호 찾기용
    public boolean isExistForPw(String name, String id, String email) {
    	if (usersDao.isExistForPw(name, id, email) == 0) {
            return false;
        }
        sendMail(email);
        return true;
    }
    
    // 실제 난수 생성 및 인증번호 메일 발송
    private void sendMail(String email) {
    	System.out.println("fromEmail=[" + fromEmail + "]");
    	System.out.println("MAIL_USERNAME ENV=[" + System.getenv("MAIL_USERNAME") + "]");
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(3);
        verificationStorage.put(email, new VerificationInfo(code, expiresAt));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject("[Orbit] 회원 인증 번호 안내");
            helper.setText("""
                <div style="font-family: sans-serif; max-width: 500px;">
                    <h2 style="color: #3530B8;">Orbit 인증번호 안내</h2>
                    <p>아래 인증번호를 3분 이내에 입력해주세요.</p>
                    <div style="font-size: 32px; font-weight: bold; color: #3530B8; 
                                background: #F0F4FF; padding: 20px; text-align: center;
                                border-radius: 12px; letter-spacing: 8px;">
                        %s
                    </div>
                    <p style="color: #999; font-size: 12px; margin-top: 16px;">
                        본 메일은 발신 전용입니다.
                    </p>
                </div>
            """.formatted(code), true); // true = HTML 사용
            
            mailSender.send(message);
            
        } catch (Exception e) {
            throw new RuntimeException("메일 발송 실패", e);
        }
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
            
            return usersDao.findIdByEmail(email);
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
    public boolean changePw(String email, String newPw, String token) {
        String savedToken = passwordResetTokens.get(email);
        if (savedToken != null && savedToken.equals(token)) {
            passwordResetTokens.remove(email);
            
            String pw = EncryptionUtils.getSha512(newPw);
            usersDao.changePw(email, pw);
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
