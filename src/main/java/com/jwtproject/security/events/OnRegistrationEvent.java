package com.jwtproject.security.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.UserDetails;
public class OnRegistrationEvent extends ApplicationEvent {
    private String appUrl;
    private UserDetails userDetails;

    public OnRegistrationEvent(UserDetails userDetails, String appUrl) {
        super(userDetails);
        this.appUrl = appUrl;
        this.userDetails = userDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public String getAppUrl() {
        return appUrl;
    }
}
