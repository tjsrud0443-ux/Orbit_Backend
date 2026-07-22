package com.study.app.commons;

public class SsnValidator {
	//암호화하기 전 단계에서 "이 13자리가 실제로 유효한 체크섬을 만족하는가"를 계산
	public static boolean isValid(String ssn) {
        String clean = ssn.replace("-", "");
        if (!clean.matches("\\d{13}")) return false;
        int[] weight = {2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5};
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(clean.charAt(i)) * weight[i];
        }
        int result = (11 - (sum % 11)) % 10;
        return result == Character.getNumericValue(clean.charAt(12));
    }
}
