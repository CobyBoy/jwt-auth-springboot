package com.jwtproject.security.account;

import com.jwtproject.security.email.EmailService;
import com.jwtproject.security.user.models.User;
import com.jwtproject.security.verificationToken.VerificationToken;
import com.jwtproject.security.verificationToken.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountActivationService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final MessageSource messageSource;
    public VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public void sendVerificationEmail(User user, String token, String appUrl) {
        String to = user.getEmail();
        String confirmationUrl = appUrl + "/api/v1/registrationConfirm?token=" + token;
        var subject = messageSource.getMessage("email.confirmation.subject", null, null);
        var emailBody = messageSource.getMessage("email.confirmation.body", new Object[]{user.getFirstName(), user.getLastName(), confirmationUrl, subject,
        "${spring.application.name}"}, null);
        emailService.sendEmail(to, subject, emailBody);
    }
}
