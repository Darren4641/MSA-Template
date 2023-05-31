package com.example.springuserservice.member.repository;

import com.example.springuserservice.member.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKeyId(String keyId);
    void deleteByKeyId(String keyId);
}
