package com.jwtlogin.email.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MailConfig {
    private final MailConfigProperties mailConfigProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfigProperties.getHost());
        mailSender.setPort(mailConfigProperties.getPort());

        mailSender.setUsername(mailConfigProperties.getUsername());
        mailSender.setPassword(mailConfigProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailConfigProperties.getProtocol());
        props.put("mail.smtp.auth", mailConfigProperties.getAuth());
        props.put("mail.smtp.starttls.enable", mailConfigProperties.getTlsEnable());
        props.put("mail.debug", mailConfigProperties.getDebug());

        return mailSender;
    }

}
