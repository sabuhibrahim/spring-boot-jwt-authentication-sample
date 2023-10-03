package com.auth.jwt.auth.forms;

import com.auth.jwt.auth.validators.TokenIsValid;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshForm {
  @NotEmpty
  @TokenIsValid
  private String refresh;
}
