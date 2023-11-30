package com.jwtlogin.auth;

import com.jwtlogin.auth.models.LoginRequest;
import com.jwtlogin.auth.models.AuthenticationResponse;
import com.jwtlogin.auth.models.SignUpRequest;
import com.jwtlogin.events.OnLoginEvent;
import com.jwtlogin.events.OnRegistrationEvent;
import com.jwtlogin.jwt.JwtService;
import com.jwtlogin.user.UserService;
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
        UserDetails userDetails = userService.login(request);
        eventPublisher.publishEvent(new OnLoginEvent(userDetails, webRequest));
        String jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
