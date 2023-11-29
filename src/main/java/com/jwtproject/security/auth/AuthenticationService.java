package com.jwtproject.security.auth;

import com.jwtproject.security.auth.models.LoginRequest;
import com.jwtproject.security.auth.models.AuthenticationResponse;
import com.jwtproject.security.auth.models.SignUpRequest;
import com.jwtproject.security.events.OnRegistrationEvent;
import com.jwtproject.security.jwt.JwtService;
import com.jwtproject.security.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthenticationResponse signUp(SignUpRequest request, WebRequest webRequest) {
        UserDetails user = userService.signUp(request);
        eventPublisher.publishEvent(new OnRegistrationEvent(user, webRequest.getContextPath()));

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse login(LoginRequest request, HttpServletRequest webRequest) {
        String jwtToken = jwtService.generateToken(userService.login(request, webRequest));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
