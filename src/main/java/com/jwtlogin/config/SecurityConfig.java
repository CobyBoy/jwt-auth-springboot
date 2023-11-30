package com.jwtlogin.config;

import com.jwtlogin.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (auth) -> {
                            auth
                                    .requestMatchers(HttpMethod.GET,"/actuator/**").permitAll();
                            auth
                                    .requestMatchers(HttpMethod.GET,"/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                            auth
                                    .requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll();
                            auth
                                    .requestMatchers("/api/v1/admin/**").hasRole("ADMIN");
                            auth
                                    .requestMatchers(HttpMethod.GET,"/api/v1/confirm-account").permitAll();
                            auth
                                    .anyRequest().authenticated();
                        }
                )
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }
}
