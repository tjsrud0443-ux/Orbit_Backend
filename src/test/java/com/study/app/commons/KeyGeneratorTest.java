package com.study.app.commons;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGeneratorTest {
	public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        
        // 32바이트 키 생성
        byte[] key = new byte[32];
        random.nextBytes(key);
        
        // 16바이트 IV 생성
        byte[] iv = new byte[16];
        random.nextBytes(iv);

        // 출력된 값을 설정 파일에 복사해서 사용
        System.out.println("키(32자 추천): " + Base64.getEncoder().encodeToString(key).substring(0, 32));
        System.out.println("IV(16자 추천): " + Base64.getEncoder().encodeToString(iv).substring(0, 16));
    }
}
