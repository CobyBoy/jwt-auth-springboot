package com.jwtproject.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class AccountAlreadyVerifiedException extends RuntimeException{
    private final String message;
}
