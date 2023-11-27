package com.jwtproject.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class democontroller {
    @GetMapping
    public String getDemo() {
        return "Authenticated";
    }

}
