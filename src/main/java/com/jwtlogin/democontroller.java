package com.jwtlogin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class democontroller {
    @GetMapping
    public String getDemo(HttpServletRequest request) {
        return "Authenticated " + request.getHeader("X-Forwarded-For") + "remote addr "+ request.getRemoteAddr() + "remote host "+request.getRemoteHost();
    }

}
