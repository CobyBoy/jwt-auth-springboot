package com.jwtproject.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String jwt;
            final String userEmail;
            jwt = getTokenFromHeader(request);
            if (jwt != null) {
                userEmail = jwtService.extractUsername(jwt);
                if (usernameIsNotNullAndIsNotInAuthContext(userEmail)) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                    if (isTokenValid(jwt, userDetails)) {
                        log.info("Token is valid: {}", jwt);
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (Exception ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }

    }

    private String getTokenFromHeader(HttpServletRequest request) {
        final String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        log.warn("No header auth token was found");
        return null;
    }

    private boolean usernameIsNotNullAndIsNotInAuthContext(String userEmail) {
        return userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private boolean isTokenValid(String jwt, UserDetails userDetails) {
        return jwtService.isTokenValid(jwt, userDetails);
    }
}
