package com.jwtproject.security.registration;

import com.jwtproject.security.account.AccountActivationService;
import com.jwtproject.security.email.EmailService;
import com.jwtproject.security.events.OnRegistrationEvent;
import com.jwtproject.security.user.models.User;
import com.jwtproject.security.verificationToken.VerificationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final AccountActivationService accountActivationService;
    private final MessageSource messageSource;
    private final EmailService emailService;
    @Value("${spring.application.name}")
    private String appName;
    public void onUserRegistration(OnRegistrationEvent event) {
        log.info("confirm sign up event {}", event);
        var user = (User) event.getUserDetails();
        VerificationToken verificationToken = accountActivationService.createVerificationToken(user);
        log.error("User does not have a verification token, create one and send it through email");
        createRegistrationEmail(user, verificationToken.getToken(), event.getAppUrl());
    }

    public void createRegistrationEmail(@NotNull User user, String token, String appUrl) {
        String to = user.getEmail();
        String confirmationUrl = appUrl + "/api/v1/confirm-account?token=" + token;
        var subject = messageSource.getMessage("email.confirmation.subject", null, null);
        var emailBody = messageSource.getMessage("email.confirmation.body", new Object[]{
                user.getFirstName(),
                user.getLastName(),
                confirmationUrl,
                subject,
                appName}, null);
        emailService.sendEmail(to, subject, emailBody);
    }
}
