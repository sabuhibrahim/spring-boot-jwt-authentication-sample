package com.auth.jwt.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.jwt.auth.models.BlackListToken;

public interface BlackListTokenRepository extends JpaRepository<BlackListToken, String> {
  Optional<BlackListToken> findById(String id);

  boolean existsById(String id);
}
