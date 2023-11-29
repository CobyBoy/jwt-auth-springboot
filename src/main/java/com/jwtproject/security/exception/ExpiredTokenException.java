package com.jwtproject.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter

public class ExpiredTokenException extends RuntimeException{
    private final String message;
}
