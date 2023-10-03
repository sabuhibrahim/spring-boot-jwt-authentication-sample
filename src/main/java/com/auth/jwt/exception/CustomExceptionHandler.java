package com.auth.jwt.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.annotation.Nullable;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  @Nullable
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    Map<String, Object> responseBody = new LinkedHashMap<>();
    responseBody.put("timestamp", new Date());
    responseBody.put("status", status.value());

    List<String> errors = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(x -> x.getField() + ": " + x.getDefaultMessage())
        .collect(Collectors.toList());

    responseBody.put("errors", errors);

    return handleExceptionInternal(ex, responseBody, headers, status, request);
  }

  @ExceptionHandler(FormNotValidException.class)
  public ResponseEntity<Object> handleCustomFormNotValid(FormNotValidException ex, WebRequest request) {
    Map<String, Object> responseBody = new LinkedHashMap<>();
    responseBody.put("timestamp", new Date());
    responseBody.put("status", HttpStatus.BAD_REQUEST.value());
    responseBody.put("errors", new String[] { ex.getMessage() });
    HttpHeaders headers = new HttpHeaders();
    return handleExceptionInternal(ex, responseBody, headers, HttpStatus.BAD_REQUEST, request);
  }
}
