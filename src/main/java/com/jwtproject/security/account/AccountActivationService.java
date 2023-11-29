package com.jwtproject.security.account;

import com.jwtproject.security.email.EmailService;
import com.jwtproject.security.exception.TokenNotFoundException;
import com.jwtproject.security.user.UserRepository;
import com.jwtproject.security.user.models.User;
import com.jwtproject.security.verificationToken.VerificationToken;
import com.jwtproject.security.verificationToken.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountActivationService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final MessageSource messageSource;
    private final UserRepository userRepository;
    public VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public void deleteVerificationToken(VerificationToken verificationToken) {
        verificationToken.setIsExpired(true);
        verificationTokenRepository.delete(verificationToken);
    }

    public void sendVerificationEmail(User user, String token, String appUrl) {
        String to = user.getEmail();
        String confirmationUrl = appUrl + "/api/v1/confirm-account?token=" + token;
        var subject = messageSource.getMessage("email.confirmation.subject", null, null);
        var emailBody = messageSource.getMessage("email.confirmation.body", new Object[]{user.getFirstName(), user.getLastName(), confirmationUrl, subject,
        "${spring.application.name}"}, null);
        emailService.sendEmail(to, subject, emailBody);
    }
    public void activateAccount(String confirmationToken) {
       VerificationToken userToken = verificationTokenRepository.findByToken(confirmationToken).orElseThrow(() -> new TokenNotFoundException("Token was not found. Register again."));

       if(!userToken.getIsExpired()) {
           var user = userToken.getUser();
           userToken.setConfirmedAt(LocalDateTime.now());
           user.setIsAccountVerified(true);
           user.setConfirmedRegistrationAt(LocalDateTime.now());
           userRepository.save(user);
           verificationTokenRepository.save(userToken);
       }
       else {
           log.error("token on confirm account is expired");
       }

    }
}
