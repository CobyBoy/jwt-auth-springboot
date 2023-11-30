package com.jwtlogin.email.impl;

import com.jwtlogin.email.config.MailConfigProperties;
import com.jwtlogin.exception.MailSenderException;
import com.jwtlogin.email.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final MailConfigProperties mailConfigProperties;
    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setText(body, true);
            helper.setSubject(subject);
            helper.setFrom(mailConfigProperties.getUsername());
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new MailSenderException(e.getMessage());
        }
    }
}
