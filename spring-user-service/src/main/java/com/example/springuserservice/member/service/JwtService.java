package com.example.springuserservice.member.service;

import com.example.springuserservice.member.config.JwtTokenProvider;
import com.example.springuserservice.member.dto.CustomResponse;
import com.example.springuserservice.member.dto.MemberDto;
import com.example.springuserservice.member.dto.Token;
import com.example.springuserservice.member.entity.RefreshToken;
import com.example.springuserservice.member.handler.CustomException;
import com.example.springuserservice.member.handler.ErrorCode;
import com.example.springuserservice.member.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@AllArgsConstructor
@Service
public class JwtService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Token login(HttpServletRequest request, MemberDto memberDto, String userAgent) {
        CustomResponse.ResponseMap result = null;
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(memberDto.getEmail(), memberDto.getPassword())
        );

        return getLoginToken(memberDto.getEmail(), userAgent);

    }

    private Token getLoginToken(String email, String userAgent) {
        Token token = jwtTokenProvider.createToken(email);
        //RefreshToken을 DB에 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .keyId(token.getKey())
                .refreshToken(token.getRefreshToken())
                .userAgent(userAgent).build();

        Optional<RefreshToken> tokenOptional = refreshTokenRepository.findByKeyId(email);

        //refreshToken이 있는지 검사
        if(tokenOptional.isEmpty()) {
            refreshTokenRepository.save(
                    RefreshToken.builder()
                            .keyId(token.getKey())
                            .refreshToken(token.getRefreshToken())
                            .userAgent(userAgent).build());
        }else {
            //refreshToken이 있으면, 업데이트
            refreshToken.update(tokenOptional.get().getRefreshToken(), tokenOptional.get().getUserAgent());

        }


        return token;
    }

    public String getNewAccessToken(RefreshToken refreshToken) {
        if(refreshToken.getRefreshToken() != null)
            return jwtTokenProvider.validateRefreshToken(refreshToken);
        else
            throw new CustomException(ErrorCode.ReLogin);
    }

}
