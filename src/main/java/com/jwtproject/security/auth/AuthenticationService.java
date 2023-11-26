package com.jwtproject.security.auth;

import com.jwtproject.security.exception.UserNotFoundException;
import com.jwtproject.security.jwt.JwtService;
import com.jwtproject.security.exception.UserAlreadyExistsException;
import com.jwtproject.security.user.User;
import com.jwtproject.security.user.UserRepository;
import com.jwtproject.security.user.UserRoles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        User newUser = null;
        if (user.isEmpty()) {
            newUser = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .userRole(UserRoles.USER)
                    .build();
            userRepository.save(newUser);
            log.info("Saving user: {}", newUser);
        } else {
            log.error("User already registered");
            throw new UserAlreadyExistsException("An account is already registered with your email address. Please log in.");
        }
        String jwtToken = jwtService.generateToken(newUser);


        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Authenticating user");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User was not found"));
        log.info("Searching user by email");

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
