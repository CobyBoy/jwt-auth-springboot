package com.jwtproject.security.exception;

import com.jwtproject.security.exception.dto.ApiErrorDto;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
* Handles exceptions thrown by controllers
*/
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        log.error("Validation failed");
        List<String> errors = new ArrayList<>();
        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(httpStatus)
                .body(ApiErrorDto.builder()
                        .httpStatus(httpStatus)
                        .statusCode(httpStatus.value())
                        .message(errors.toString().substring(1, errors.toString().length()-1))
                        .additionalInfo(errors)
                        .build());
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.info("User already exists exception");
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        return ResponseEntity.status(httpStatus)
                .body(ApiErrorDto.builder()
                        .httpStatus(httpStatus)
                        .statusCode(httpStatus.value())
                        .message(ex.getMessage())
                        .build()
                );
    }
/**
* Exceptions thrown by the filter
* */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorDto> handleRuntimeException(RuntimeException ex) {
        log.info("Run time exception thrown");
        if (ex instanceof SignatureException) {
            HttpStatus httpStatus = HttpStatus.FORBIDDEN;
            return ResponseEntity.status(httpStatus)
                    .body(ApiErrorDto.builder()
                            .httpStatus(httpStatus)
                            .statusCode(httpStatus.value())
                            .message("Token is not valid")
                            .build()
                    );
        }

        if (ex instanceof UsernameNotFoundException) {
            HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
            return ResponseEntity.status(httpStatus)
                    .body(ApiErrorDto.builder()
                            .httpStatus(httpStatus)
                            .statusCode(httpStatus.value())
                            .message(ex.getMessage())
                            .build()
                    );
        }
        HttpStatus httpStatus = HttpStatus.BAD_GATEWAY;
        return ResponseEntity.status(httpStatus)
                .body(ApiErrorDto.builder()
                        .httpStatus(httpStatus)
                        .statusCode(httpStatus.value())
                        .message(ex.getMessage())
                        .build()
                );
    }
}
