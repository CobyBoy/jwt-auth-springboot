package com.jwtproject.security.exception;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class AccountNotVerifiedException extends RuntimeException{
    private final String message;
}
