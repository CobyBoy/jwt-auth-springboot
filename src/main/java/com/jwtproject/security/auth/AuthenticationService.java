package com.jwtproject.security.auth;

import com.jwtproject.security.auth.models.LoginRequest;
import com.jwtproject.security.auth.models.AuthenticationResponse;
import com.jwtproject.security.auth.models.SignUpRequest;
import com.jwtproject.security.jwt.JwtService;
import com.jwtproject.security.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationResponse signUp(SignUpRequest request) {

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(userService.signUp(request)))
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        String jwtToken = jwtService.generateToken(userService.login(request));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
