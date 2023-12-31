package com.jwtlogin.events;

import com.jwtlogin.user.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class OnRegistrationCompletedEvent extends ApplicationEvent {

    private final User user;

    public OnRegistrationCompletedEvent(User user) {
        super(user);
        this.user = user;
    }
}
