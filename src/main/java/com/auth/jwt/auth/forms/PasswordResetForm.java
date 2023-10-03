package com.auth.jwt.auth.forms;

import com.auth.jwt.auth.validators.PasswordMatch;

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
public class PasswordResetForm {
  @NotEmpty
  private String password;
  @NotEmpty
  private String confirmPassword;
}
