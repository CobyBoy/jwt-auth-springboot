package com.jwtproject.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class JwtConfig {
    @Value("${jwt.secret.key}")
    private String jwtSecretKey;
    @Value("${jwt.secret.expiration.in.miliseconds}")
    private String expirationInMiliseconds;

    public String getJwtSecretKey() {
        return jwtSecretKey;
    }
    public String getExpirationInMiliseconds() { return expirationInMiliseconds; }
}
