package com.jwtlogin.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter

public class MailSenderException extends RuntimeException{
    private final String message;
}