package com.auth.jwt.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwt.auth.forms.AccessToken;
import com.auth.jwt.auth.forms.ForgotPasswordForm;
import com.auth.jwt.auth.forms.LoginForm;
import com.auth.jwt.auth.forms.PasswordResetForm;
import com.auth.jwt.auth.forms.RefreshForm;
import com.auth.jwt.auth.forms.RegisterForm;
import com.auth.jwt.auth.forms.SuccessResponse;
import com.auth.jwt.auth.forms.TokenPairs;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<TokenPairs> register(@Valid @RequestBody RegisterForm registerForm) {
    return ResponseEntity.ok(authService.register(registerForm));
  }

  @PostMapping("/login")
  public ResponseEntity<TokenPairs> login(@Valid @RequestBody LoginForm loginForm) {
    return ResponseEntity.ok(authService.login(loginForm));
  }

  @PostMapping("/refresh")
  public ResponseEntity<AccessToken> refresh(@Valid @RequestBody RefreshForm refreshForm) {
    return ResponseEntity.ok(authService.refresh(refreshForm));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<SuccessResponse> forgotPassword(@Valid @RequestBody ForgotPasswordForm forgotPasswordForm) {
    return ResponseEntity.ok(authService.forgotPassword(forgotPasswordForm));
  }

  @PostMapping("/password-reset")
  public ResponseEntity<SuccessResponse> passwordReset(@Valid @RequestBody PasswordResetForm passwordResetForm,
      @RequestParam("token") String token) throws Exception {
    return ResponseEntity.ok(authService.passwordReset(passwordResetForm, token));
  }
}
