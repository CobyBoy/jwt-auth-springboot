package com.jwtproject.security.user;

import com.jwtproject.security.auth.models.LoginRequest;
import com.jwtproject.security.auth.models.SignUpRequest;
import com.jwtproject.security.exception.UserAlreadyExistsException;
import com.jwtproject.security.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserDetails signUp(@NotNull SignUpRequest request) {
        User user = findUserByEmail(request.getEmail());
        if (user == null) {
            try {
                return userRepository.save(User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .userRole(UserRoles.USER)
                        .isAccountVerified(false)
                        .accessFailedCount(0)
                        .registeredAt(LocalDateTime.now())
                        .build());
            }
            catch (DataIntegrityViolationException ex) {
                throw new RuntimeException("There was an error registering the user");
            }

        } else {
            throw new UserAlreadyExistsException("An account is already registered with your email address. Please log in.");
        }

    }

    private @Nullable User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    public UserDetails login(@NotNull LoginRequest request) {
        log.info("Authenticating user {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        log.info("Searching user by email: {}", request.getEmail());
        return userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User was not found"));
    }
}
