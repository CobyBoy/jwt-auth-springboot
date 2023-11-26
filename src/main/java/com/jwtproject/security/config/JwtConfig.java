package com.jwtproject.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class JwtConfig {
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;
    @Value("${jwt.secret.expiration.in.milliseconds}")
    private Long expirationInMiliseconds;

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }
    public Long getExpirationInMiliseconds() { return expirationInMiliseconds; }
}
