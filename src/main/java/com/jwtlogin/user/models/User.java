package com.jwtlogin.user.models;

import com.jwtlogin.audit.AuthenticationLog;
import com.jwtlogin.verificationToken.VerificationToken;
import com.jwtlogin.user.UserRoles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public static final int MAX_LOGIN_ATTEMPT = 10;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean isAccountVerified;
    private LocalDateTime registeredAt;
    private LocalDateTime confirmedRegistrationAt;
    private Integer accessFailedCount;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VerificationToken verificationToken;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<AuthenticationLog> auditLogin;
    @Enumerated(EnumType.STRING)
    private UserRoles userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(this.getAccessFailedCount() == MAX_LOGIN_ATTEMPT);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAccountVerified;
    }
}
