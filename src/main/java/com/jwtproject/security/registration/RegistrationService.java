package com.jwtproject.security.registration;

import com.jwtproject.security.account.AccountActivationService;
import com.jwtproject.security.events.OnRegistrationEvent;
import com.jwtproject.security.user.models.User;
import com.jwtproject.security.verificationToken.VerificationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class RegistrationService {
    private final AccountActivationService accountActivationService;
    @Async
    public void onSignUpConfirmation(OnRegistrationEvent event) {
        log.info("confirm registration event {}", event);
        var user = (User) event.getUserDetails();
        VerificationToken verificationToken = accountActivationService.createVerificationToken(user);
        log.error("User does not have a verification token, create one and send it through email");
        accountActivationService.sendVerificationEmail(user, verificationToken.getToken(), event.getAppUrl());
    }
}
