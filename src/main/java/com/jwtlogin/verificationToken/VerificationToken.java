package com.jwtlogin.verificationToken;

import com.jwtlogin.user.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    private Boolean isExpired;
    private LocalDateTime expiryDate;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;

    public VerificationToken(UserDetails userDetails) {
        this.token = UUID.randomUUID().toString();
        this.user = (User) userDetails;
        this.createdAt = LocalDateTime.now();
        this.expiryDate = calculateExpiryDate();
        this.isExpired = isTokenExpired();
    }

    private LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(VerificationToken.EXPIRATION);
    }

    private Boolean isTokenExpired() {
       return this.expiryDate.isBefore(LocalDateTime.now());
    }
}
