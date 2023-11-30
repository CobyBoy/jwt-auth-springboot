package com.jwtlogin.auth.models;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "You must provide a first name")
    private String firstName;
    @NotBlank(message = "You must provide a last name")
    private String lastName;
    @NotBlank(message = "You must provide an email")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "You must provide a password")
    @Length(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
    private String password;
}
