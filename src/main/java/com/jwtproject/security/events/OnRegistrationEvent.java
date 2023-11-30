package com.jwtproject.security.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;
@Getter
@Setter
public class OnRegistrationEvent extends ApplicationEvent {
    private String appUrl;
    private UserDetails userDetails;

    public OnRegistrationEvent(UserDetails userDetails, String appUrl) {
        super(userDetails);
        this.appUrl = appUrl;
        this.userDetails = userDetails;
    }
}
