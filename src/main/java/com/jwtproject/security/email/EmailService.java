package com.jwtproject.security.email;

public interface EmailService {
   void sendEmail(String to, String subject, String body);
}
