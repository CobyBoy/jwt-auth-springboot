package com.jwtproject.security.account;

import com.jwtproject.security.dto.ApiResponseDto;
import com.jwtproject.security.dto.MessageType;
import com.jwtproject.security.email.EmailService;
import com.jwtproject.security.events.OnRegistrationCompletedEvent;
import com.jwtproject.security.exception.AccountAlreadyVerifiedException;
import com.jwtproject.security.exception.ExpiredTokenException;
import com.jwtproject.security.exception.TokenNotFoundException;
import com.jwtproject.security.user.UserRepository;
import com.jwtproject.security.user.models.User;
import com.jwtproject.security.verificationToken.VerificationToken;
import com.jwtproject.security.verificationToken.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountActivationService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final EmailService emailService;
    private final MessageSource messageSource;
    public VerificationToken createVerificationToken(User user) {
        VerificationToken verificationToken = new VerificationToken(user);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public void deleteVerificationToken(VerificationToken verificationToken) {
        verificationToken.setIsExpired(true);
        verificationTokenRepository.delete(verificationToken);
    }

    public ApiResponseDto activateAccount(String confirmationToken) {
       VerificationToken userToken = verificationTokenRepository.findByToken(confirmationToken)
               .orElseThrow(() -> new TokenNotFoundException("Token was not found."));

       if(isUserTokenNotExpiredAndAccountNotVerified(userToken)) {
           User user = userToken.getUser();
           userToken.setConfirmedAt(LocalDateTime.now());
           user.setIsAccountVerified(true);
           user.setConfirmedRegistrationAt(LocalDateTime.now());
           userRepository.save(user);
           verificationTokenRepository.save(userToken);
           eventPublisher.publishEvent(new OnRegistrationCompletedEvent(user));
           return new ApiResponseDto<>("Account has been successfully verified. You can now log in", MessageType.SUCCESS);
       }
       else if (userToken.getUser().getIsAccountVerified()){
           throw new AccountAlreadyVerifiedException("Account has already been verified");
       }
       throw new ExpiredTokenException("Verification token has expired");
    }

    public Boolean isUserTokenNotExpiredAndAccountNotVerified(VerificationToken userToken) {
        return !userToken.getIsExpired() && !userToken.getUser().getIsAccountVerified();
    }

    public void onUserCompletedRegistration(OnRegistrationCompletedEvent event) {
        log.info("onUserCompletedRegistration event {}", event.getUser());
        createActivationWelcomeEmail(event.getUser());
    }

    public void createActivationWelcomeEmail(@NotNull User user) {
        String to = user.getEmail();
        String subject = messageSource.getMessage("email.enabled.account.subject", null, null);
        String body = messageSource.getMessage("email.enabled.account.body", new Object[]{
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        }, null);
        emailService.sendEmail(to, subject, body);
    }
}
