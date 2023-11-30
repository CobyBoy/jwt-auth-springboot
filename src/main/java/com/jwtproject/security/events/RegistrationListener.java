package com.jwtproject.security.events;

import com.jwtproject.security.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableAsync
public class RegistrationListener implements ApplicationListener<OnRegistrationEvent> {
    private final RegistrationService registrationService;

    @Override
    @Async
    public void onApplicationEvent(@NotNull OnRegistrationEvent event) {
        registrationService.onUserRegistration(event);
    }
}
