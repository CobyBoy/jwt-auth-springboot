package com.jwtproject.security.user.models;

import com.jwtproject.security.audit.AuthenticationLog;
import com.jwtproject.security.verificationToken.VerificationToken;
import com.jwtproject.security.user.UserRoles;
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
        return true;
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
