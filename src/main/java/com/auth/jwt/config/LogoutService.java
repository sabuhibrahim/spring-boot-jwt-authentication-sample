package com.auth.jwt.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.auth.jwt.auth.models.BlackListToken;
import com.auth.jwt.auth.repositories.BlackListTokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final BlackListTokenRepository blackListTokenRepository;
  private final JwtService jwtService;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    final String authHeader = request.getHeader("Authorization");
    final String jwtToken;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }

    jwtToken = authHeader.split(" ")[1];

    if (!jwtService.isTokenValid(jwtToken)) {
      return;
    }

    final String id = jwtService.extractId(jwtToken);

    BlackListToken blackListToken = new BlackListToken();
    blackListToken.setId(id);

    blackListTokenRepository.save(blackListToken);
  }

}
