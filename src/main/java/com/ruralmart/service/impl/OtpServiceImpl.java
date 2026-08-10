package com.ruralmart.service.impl;

import com.ruralmart.exception.OtpValidationException;
import com.ruralmart.otp.OtpData;
import com.ruralmart.otp.OtpPurpose;
import com.ruralmart.service.EmailService;
import com.ruralmart.service.OtpService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpServiceImpl implements OtpService {

    private static final long OTP_VALID_MINUTES = 5;

    private final SecureRandom secureRandom = new SecureRandom();
    private final EmailService emailService;

    // key = "PURPOSE|email", value = current OTP + expiry.
    // In-memory only, by design - never written to the users table.
    private final ConcurrentHashMap<String, OtpData> otpStore = new ConcurrentHashMap<>();

    public OtpServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void generateAndSendOtp(String email, OtpPurpose purpose) {
        String otp = generateSixDigitOtp();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(OTP_VALID_MINUTES);

        // A new request replaces any previous OTP for this email+purpose.
        otpStore.put(key(email, purpose), new OtpData(otp, expiresAt));

        emailService.sendOtpEmail(email, otp, purpose);

        // Dev-only convenience log so you can see it without checking your
        // inbox while testing locally. Remove before a real production
        // deploy - printing OTPs to logs isn't something you'd want live.
        System.out.println("[DEV] " + purpose + " OTP for " + email + ": " + otp + " (expires in 5 min)");
    }

    @Override
    public void verifyOtp(String email, String otp, OtpPurpose purpose) {
        String key = key(email, purpose);
        OtpData stored = otpStore.get(key);

        if (stored == null) {
            throw new OtpValidationException("OTP not found. Please request a new OTP.");
        }

        if (stored.isExpired()) {
            otpStore.remove(key);
            throw new OtpValidationException("OTP has expired. Please request a new OTP.");
        }

        if (!stored.getOtp().equals(otp)) {
            throw new OtpValidationException("Invalid OTP.");
        }

        // Correct and not expired - consume it so it can't be reused.
        otpStore.remove(key);
    }

    private String key(String email, OtpPurpose purpose) {
        return purpose.name() + "|" + email;
    }

    private String generateSixDigitOtp() {
        // 100000-999999 guarantees exactly 6 digits (never a leading zero
        // that would silently produce a 5-digit number as a string).
        int otp = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(otp);
    }
}