package com.satyamagrawal.swiftbuy.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ProblemDetail handleDuplicateResourceException(DuplicateResourceException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Duplicate Resource");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("status", HttpStatus.CONFLICT.value());
        return problemDetail;
    }
}
