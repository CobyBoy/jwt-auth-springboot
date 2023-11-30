package com.jwtlogin.auth.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "You haven't provided an email")
    private String email;
    @NotBlank(message = "You haven't provided a password")
    private String password;
}
