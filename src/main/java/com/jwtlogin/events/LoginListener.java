package com.jwtlogin.events;

import com.jwtlogin.audit.AuthenticationLog;
import com.jwtlogin.audit.AuthenticationLogRepository;
import com.jwtlogin.user.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableAsync
public class LoginListener  implements ApplicationListener<OnLoginEvent> {
    private final AuthenticationLogRepository authenticationLogRepository;

    @Override
    @Async
    public void onApplicationEvent(@NotNull OnLoginEvent event) {
        log.info("Login event fired {}", event);
        authenticationLogRepository.save(AuthenticationLog.builder()
                .ipAddress(event.getWebRequest().getRemoteAddr())
                .loggedInAt(LocalDateTime.now())
                .isSuccessful(true)
                .user((User) event.getUserDetails())
                .build());
    }
}
