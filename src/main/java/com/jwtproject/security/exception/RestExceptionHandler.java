package com.jwtproject.security.exception;

import com.jwtproject.security.exception.dto.ApiErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorDto> handleRuntimeException(RuntimeException ex) {
        log.info("Run time exception thrown");
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        return ResponseEntity.status(httpStatus)
                .body(ApiErrorDto.builder()
                        .httpStatus(httpStatus)
                        .statusCode(httpStatus.value())
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleUserNotFoundExceptionException(UserNotFoundException ex) {
        log.info("Run time exception thrown");
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        return ResponseEntity.status(httpStatus)
                .body(ApiErrorDto.builder()
                        .httpStatus(httpStatus)
                        .statusCode(httpStatus.value())
                        .message(ex.getMessage())
                        .build()
                );
    }

}
