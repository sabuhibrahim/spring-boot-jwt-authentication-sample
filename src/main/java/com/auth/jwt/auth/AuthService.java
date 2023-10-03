package com.auth.jwt.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.auth.jwt.auth.forms.AccessToken;
import com.auth.jwt.auth.forms.ForgotPasswordForm;
import com.auth.jwt.auth.forms.LoginForm;
import com.auth.jwt.auth.forms.PasswordResetForm;
import com.auth.jwt.auth.forms.RefreshForm;
import com.auth.jwt.auth.forms.RegisterForm;
import com.auth.jwt.auth.forms.SuccessResponse;
import com.auth.jwt.auth.forms.TokenPairs;
import com.auth.jwt.auth.models.User;
import com.auth.jwt.auth.repositories.UserRepository;
import com.auth.jwt.auth.utils.Role;
import com.auth.jwt.config.JwtService;
import com.auth.jwt.exception.FormNotValidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  public final AuthenticationManager authenticationManager;

  public TokenPairs register(RegisterForm registerForm) throws FormNotValidException {

    if (userRepository.existsByEmail(registerForm.getEmail())) {
      throw new FormNotValidException("email: Email already taken");
    }

    User user = User.builder()
        .email(registerForm.getEmail())
        .fullName(registerForm.getFullName())
        .password(passwordEncoder.encode(registerForm.getPassword()))
        .role(Role.USER)
        .build();
    userRepository.save(user);
    return jwtService.generateTokenPair(user);
  }

  public TokenPairs login(LoginForm loginForm) {
    authenticationManager
        .authenticate(
            new UsernamePasswordAuthenticationToken(
                loginForm.getEmail(),
                loginForm.getPassword()));
    User user = userRepository.findByEmail(loginForm.getEmail()).orElseThrow();
    return jwtService.generateTokenPair(user);
  }

  public AccessToken refresh(RefreshForm refreshForm) {
    String access = jwtService
        .generateAccessTokenFromRefreshClaims(jwtService.extractAllClaims(refreshForm.getRefresh()));
    return AccessToken.builder()
        .access(access)
        .build();
  }

  public SuccessResponse forgotPassword(ForgotPasswordForm forgotPasswordForm) {
    User user;
    try {
      user = userRepository.findByEmail(forgotPasswordForm.getEmail()).get();
    } catch (Exception e) {
      user = null;
    }

    if (user != null) {
      String token = jwtService.generateResetToken(user);
      // now Print token to console because we use it in reset-token api
      System.out.println(token);

      // Send reset link to user mail here
    }
    return SuccessResponse.builder()
        .message("Successfully sended")
        .build();
  }

  public SuccessResponse passwordReset(PasswordResetForm passwordResetForm, String token) throws Exception {
    if (!jwtService.isResetTokenValid(token))
      throw new Exception("Token is invalid");
    String email = jwtService.extractEmail(token);
    User user = userRepository.findByEmail(email).orElseThrow();
    user.setPassword(passwordEncoder.encode(passwordResetForm.getPassword()));
    userRepository.save(user);
    return SuccessResponse.builder()
        .message("Succesfully updated")
        .build();
  }
}
