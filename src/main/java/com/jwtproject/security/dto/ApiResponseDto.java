package com.jwtproject.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiResponseDto<T> {
    private T data;
    private String message;
    private MessageType messageType;

    public ApiResponseDto(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }
}
