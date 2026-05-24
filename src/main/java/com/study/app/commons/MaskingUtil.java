package com.study.app.commons;

public class MaskingUtil {
	public static String masking(String ssn) {

		String masked = ssn.replace("-", "");

		return masked.substring(0, 6) + "-" + masked.substring(6, 7) + "******";
	}
}
