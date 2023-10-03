package com.auth.jwt.auth.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
  @NotEmpty
  @Email
  private String email;
  @NotEmpty
  private String password;
}
