package com.example.springapigateway.user.repository;

import com.example.springapigateway.security.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByKeyId(String keyId);
    void deleteByKeyId(String keyId);

}
