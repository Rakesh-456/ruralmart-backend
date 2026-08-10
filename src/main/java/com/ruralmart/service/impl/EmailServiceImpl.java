package com.ruralmart.service.impl;

import com.ruralmart.otp.OtpPurpose;
import com.ruralmart.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromAddress;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOtpEmail(String toEmail, String otp, OtpPurpose purpose) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(toEmail);
        message.setSubject(subjectFor(purpose));
        message.setText(bodyFor(otp, purpose));

        mailSender.send(message);
    }

    private String subjectFor(OtpPurpose purpose) {
        if (purpose == OtpPurpose.PASSWORD_RESET) {
            return "Your RuralMart password reset code";
        }
        return "Your RuralMart verification code";
    }

    private String bodyFor(String otp, OtpPurpose purpose) {
        String intro = purpose == OtpPurpose.PASSWORD_RESET
                ? "Your RuralMart password reset code is: "
                : "Your RuralMart verification code is: ";

        return intro + otp + "\n\n" +
                "This code expires in 5 minutes.\n\n" +
                "If you didn't request this, you can safely ignore this email.";
    }
}