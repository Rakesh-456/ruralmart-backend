package com.ruralmart.service;

import com.ruralmart.otp.OtpPurpose;

public interface EmailService {

    // Sends the OTP to the given address, with subject/wording appropriate
    // to why it was sent (account verification vs password reset).
    void sendOtpEmail(String toEmail, String otp, OtpPurpose purpose);
}