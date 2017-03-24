package com.example.util;

import io.jsonwebtoken.JwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorExceptions {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMissingInput(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> data = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            data.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("code", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", data);
        return errorResponse;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadInputType(HttpMessageNotReadableException e) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("code", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", e.getMostSpecificCause().getMessage());
        return errorResponse;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.put("message", e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleJwtException(JwtException e) {
        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("code", HttpStatus.UNAUTHORIZED.value());
        errorResponse.put("message", "Invalid token");
        return errorResponse;
    }

}