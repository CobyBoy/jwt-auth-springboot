package com.jwtproject.security.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfigProperties {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private Integer port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.properties.[mail.transport.protocol]}")
    private String protocol;
    @Value("${spring.mail.properties.[mail.smtp.auth]}")
    private String auth;
    @Value("${spring.mail.properties.[mail.smtp.starttls.enable]}")
    private String tlsEnable;
    @Value("${spring.mail.properties.[mail.debug]}")
    private String debug;

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getAuth() {
        return auth;
    }

    public String getTlsEnable() {
        return tlsEnable;
    }

    public String getDebug() {
        return debug;
    }
}