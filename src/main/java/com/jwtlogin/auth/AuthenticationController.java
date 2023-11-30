package com.jwtlogin.auth;

import com.jwtlogin.auth.models.AuthenticationResponse;
import com.jwtlogin.auth.models.LoginRequest;
import com.jwtlogin.auth.models.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signUp(WebRequest webRequest, @RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request, webRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(HttpServletRequest webRequest, @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request, webRequest));
    }
}
