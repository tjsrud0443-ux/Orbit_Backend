package com.study.app.domains.auth;

import java.time.LocalDateTime;

public class VerificationInfo {
	private final String code;
    private final LocalDateTime expiresAt;

    public VerificationInfo(String code, LocalDateTime expiresAt) {
        this.code = code;
        this.expiresAt = expiresAt;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
