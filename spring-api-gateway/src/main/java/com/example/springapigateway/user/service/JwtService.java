package com.example.springapigateway.user.service;

import com.example.springapigateway.exception.CustomException;
import com.example.springapigateway.jwt.JwtTokenProvider;
import com.example.springapigateway.response.CustomResponse;
import com.example.springapigateway.security.handler.AuthCode;
import com.example.springapigateway.security.handler.AuthenticationCustomException;
import com.example.springapigateway.security.token.RefreshToken;
import com.example.springapigateway.security.token.Token;
import com.example.springapigateway.user.dto.MemberDto;
import com.example.springapigateway.user.repository.RefreshTokenRepository;
import com.example.springapigateway.user.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository MemberRepository;

    @Transactional
    public CustomResponse login(HttpServletRequest request, MemberDto memberDto, String userAgent) {

        CustomResponse.ResponseMap result = null;
        try {

            /*Optional<User> alsoUser = UserRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
            if(alsoUser.isEmpty())
                UserRepository.save(User.builder()
                        .email(userDto.getEmail())
                        .password(passwordEncoder.encode(userDto.getPassword()))
                        .role(Role.USER.getValue()).build()
                );
            */
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(memberDto.getEmail(), memberDto.getPassword())
            );

            Token token = getLoginToken(memberDto.getEmail(), userAgent);

            result = new CustomResponse.ResponseMap(200, "accessToken", token.getAccessToken());
            result.setResponseData("refreshToken", token.getRefreshToken());
            result.setResponseData("key", token.getKey());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("exception", "UsernameOrPasswordNotFoundException");
            throw new AuthenticationCustomException(AuthCode.UsernameOrPasswordNotFoundException);
        }

        return result;
    }

    private Token getLoginToken(String alsoEmail, String userAgent) {
        Token token = jwtTokenProvider.createToken(alsoEmail);
        //RefreshToken을 DB에 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .keyId(token.getKey())
                .refreshToken(token.getRefreshToken())
                .userAgent(userAgent).build();

        Optional<RefreshToken> tokenOptional = refreshTokenRepository.findByKeyId(alsoEmail);

        if(tokenOptional.isPresent()) {
            if(!tokenOptional.get().getUserAgent().equals(userAgent)) {
                refreshTokenRepository.deleteByKeyId(alsoEmail);
                refreshTokenRepository.save(refreshToken);
            }
        }else {
            refreshTokenRepository.save(refreshToken);
        }
        return token;
    }


    public CustomResponse newAccessToken(RefreshToken refreshToken) {
        if(refreshToken.getRefreshToken() != null) {
            String newToken = jwtTokenProvider.validateRefreshToken(refreshToken);
            return new CustomResponse.ResponseMap(200, "accessToken", newToken);
        }else {
            throw new CustomException(AuthCode.ReLogin);
        }
    }
}
