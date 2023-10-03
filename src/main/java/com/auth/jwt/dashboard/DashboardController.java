package com.auth.jwt.dashboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwt.auth.forms.SuccessResponse;

@RestController
@RequestMapping("api/v1/dashboard")
public class DashboardController {
  @GetMapping
  public ResponseEntity<SuccessResponse> index() {
    return ResponseEntity.ok(
        SuccessResponse
            .builder()
            .message("Congrigulation! You are index!")
            .build());
  }
}
