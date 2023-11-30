package com.jwtlogin.events;

import com.jwtlogin.account.AccountActivationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableAsync
public class RegistrationCompletedListener implements ApplicationListener<OnRegistrationCompletedEvent> {
    private final AccountActivationService accountActivationService;

    @Override
    @Async
    public void onApplicationEvent(OnRegistrationCompletedEvent event) {
        log.info("Registration was complted, send email to announce it {}", event);
        accountActivationService.onUserCompletedRegistration(event);
    }
}
