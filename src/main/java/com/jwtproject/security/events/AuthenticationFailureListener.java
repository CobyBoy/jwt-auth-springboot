package com.jwtproject.security.events;

import com.jwtproject.security.audit.AuthenticationLog;
import com.jwtproject.security.audit.AuthenticationLogRepository;
import com.jwtproject.security.user.UserRepository;
import com.jwtproject.security.user.models.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private final UserRepository userRepository;
    private final AuthenticationLogRepository authenticationLogRepository;
    private final HttpServletRequest request;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        UsernamePasswordAuthenticationToken username = (UsernamePasswordAuthenticationToken) event.getSource();
        log.info("User inserted bad credentials {}", username.getPrincipal().toString());
        Optional<User> user = userRepository.findByEmail(username.getPrincipal().toString());
        user.ifPresent(u -> {
                    u.setAccessFailedCount(u.getAccessFailedCount() + 1);
                    authenticationLogRepository.save(
                            AuthenticationLog.builder()
                                    .loggedInAt(LocalDateTime.now())
                                    .ipAddress(request.getRemoteAddr())
                                    .user(u)
                                    .isSuccessful(username.isAuthenticated())
                                    .build()
                    );
                }
        );
    }
}
