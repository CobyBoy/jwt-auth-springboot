package com.jwtproject.security.exception;

public class MailSenderException extends RuntimeException{
    public MailSenderException(String message) {
        super(message);
    }
}