package com.example.springapigateway.security.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long refreshTokenId;

    @Column(name = "refresh_token", unique = true)
    private String refreshToken;

    @Column(name = "key_id")
    private String keyId;

    @Column(name = "user_agent")
    private String userAgent;

    @Builder
    public RefreshToken(String refreshToken, String keyId, String userAgent) {
        this.refreshToken = refreshToken;
        this.keyId = keyId;
        this.userAgent = userAgent;
    }

}
