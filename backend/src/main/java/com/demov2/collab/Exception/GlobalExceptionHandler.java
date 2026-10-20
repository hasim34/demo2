package com.demov2.collab.Exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.demov2.collab.Dto.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new LinkedHashMap<>();
        errors.put("timestamp", LocalDateTime.now());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("errors", ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage()))
                .toList());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, Object> errors = new LinkedHashMap<>();
        errors.put("timestamp", LocalDateTime.now());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        // errors.put("errors", ex.getConstraintViolations()
        //         .stream()
        //         .map(v -> Map.of("field", v.getPropertyPath().toString(), "message", v.getMessage()))
        //         .toList());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler (NoUserFoundException.class)
    public ApiErrorResponse handlenoUserFound(NoUserFoundException ex){
        ApiErrorResponse  resp = new ApiErrorResponse(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(),ex.toString(),ex.getMessage());
        return resp;
    }

    @ExceptionHandler(UserNotCreatedException.class)
    public ApiErrorResponse handleUserNotCreated(UserNotCreatedException ex){
        ApiErrorResponse res = new ApiErrorResponse(LocalDateTime.now(),HttpStatus.BAD_REQUEST,ex.toString(),ex.getMessage());
        return res;
    }
}