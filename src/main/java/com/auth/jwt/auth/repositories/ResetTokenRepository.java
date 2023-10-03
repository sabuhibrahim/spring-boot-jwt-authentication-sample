package com.auth.jwt.auth.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.jwt.auth.models.ResetToken;

public interface ResetTokenRepository extends JpaRepository<ResetToken, String> {
  Optional<ResetToken> findByIdAndEmail(String id, String email);

  boolean existsByIdAndEmail(String id, String email);
}
