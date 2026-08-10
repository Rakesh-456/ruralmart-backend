package com.ruralmart.service;

import com.ruralmart.otp.OtpPurpose;

/**
 * Generates and verifies short-lived OTPs, sent by email.
 * In-memory storage (see OtpServiceImpl) - never persisted to the database.
 *
 * The `purpose` parameter keeps a registration OTP and a password-reset OTP
 * for the same email address from overwriting each other - without it, a
 * user who requests a password reset while a signup OTP is still pending
 * (same email) would silently invalidate the other one.
 */
public interface OtpService {

    // Generates a new 6-digit OTP for this email address + purpose,
    // replacing any previous unexpired one for that same pair, and
    // emails it via EmailService.
    void generateAndSendOtp(String email, OtpPurpose purpose);

    // Verifies the OTP for this email address + purpose. Throws
    // OtpValidationException if missing, expired, or incorrect. On
    // success, the OTP is consumed (removed) so it can't be reused.
    void verifyOtp(String email, String otp, OtpPurpose purpose);
}