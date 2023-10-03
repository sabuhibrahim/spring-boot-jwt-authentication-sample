package com.auth.jwt.auth.forms;

import com.auth.jwt.auth.validators.PasswordMatch;
import com.auth.jwt.auth.validators.PasswordMatchForm;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordMatch
public class RegisterForm implements PasswordMatchForm {
  @NotEmpty
  private String fullName;
  @NotEmpty
  @Email
  private String email;
  @NotEmpty
  private String password;
  @NotEmpty
  private String confirmPassword;
}
