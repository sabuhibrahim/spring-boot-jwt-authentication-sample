package com.auth.jwt.auth.validators;

public interface PasswordMatchForm {
  String getPassword();

  String getConfirmPassword();
}
