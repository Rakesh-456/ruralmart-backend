package com.ruralmart.otp;

import java.time.LocalDateTime;

/**
 * Holds a single OTP attempt in memory: the code and when it expires.
 * Never persisted - lives only in OtpServiceImpl's ConcurrentHashMap.
 */
public class OtpData {

    private final String otp;
    private final LocalDateTime expiresAt;

    public OtpData(String otp, LocalDateTime expiresAt) {
        this.otp = otp;
        this.expiresAt = expiresAt;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}