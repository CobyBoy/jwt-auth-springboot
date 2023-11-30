package com.jwtlogin.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class TokenNotFoundException extends RuntimeException{
    private final String message;
}
