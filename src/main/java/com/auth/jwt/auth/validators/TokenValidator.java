package com.auth.jwt.auth.validators;

import com.auth.jwt.config.JwtService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenValidator implements ConstraintValidator<TokenIsValid, String> {
  private final JwtService jwtService;
  private String message;

  public void initialize(TokenIsValid arg) {
    message = arg.message();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (jwtService.isTokenValid(value))
      return true;

    context.buildConstraintViolationWithTemplate(message)
        .addConstraintViolation();
    return false;
  }
}
