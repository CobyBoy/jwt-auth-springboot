package com.jwtproject.security.events;

import com.jwtproject.security.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationEvent> {
    private final RegistrationService registrationService;

    @Override
    public void onApplicationEvent(OnRegistrationEvent event) {
        log.info("{}",event.getUserDetails().isEnabled());
        if (event.getUserDetails().isEnabled()) {
            log.error("Account enable, email has already been sent and activated");
        }
        else {
            log.info("Account is not enable, send email to confirm registration");
            registrationService.onSignUpConfirmation(event);
        }
    }
}
