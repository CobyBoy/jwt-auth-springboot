package com.jwtlogin.events;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
public class OnLoginEvent extends ApplicationEvent {
    private final HttpServletRequest webRequest;
    private final UserDetails userDetails;

    public OnLoginEvent(UserDetails userDetails, HttpServletRequest webRequest) {
        super(userDetails);
        this.webRequest = webRequest;
        this.userDetails = userDetails;
    }
}
