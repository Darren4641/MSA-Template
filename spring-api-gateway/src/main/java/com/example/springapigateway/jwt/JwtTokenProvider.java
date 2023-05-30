package com.example.springapigateway.jwt;

import com.example.springapigateway.exception.CustomException;
import com.example.springapigateway.security.handler.AuthCode;
import com.example.springapigateway.security.handler.AuthenticationCustomException;
import com.example.springapigateway.security.token.RefreshToken;
import com.example.springapigateway.security.token.Token;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("${security.access.key}")
    private String accessSecretKey;
    @Value("${security.refresh.key}")
    private String refreshSecretKey;

    //유효시간 7일
    private long accessTokenValidTime = 7 * 24 * 60 * 60 * 1000L;
    //유효시간 31일
    private long refreshTokenValidTime = 30 * 24 * 60 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    //secretKey를 Base64로 인코딩
    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }

    //jwt 토큰 생성
    public Token createToken(String userPk) {
        Claims claims = Jwts.claims().setSubject(userPk);// JWT PayLoad에 저장되는 정보단위, PK값
        Date currentTime = new Date();
        try {
            String accessToken = getToken(claims, currentTime, accessTokenValidTime, accessSecretKey);
            String refreshToken = getToken(claims, currentTime, refreshTokenValidTime, refreshSecretKey);
            return Token.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .key(userPk).build();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;

    }

    private String getToken(Claims claims, Date currentTime, long tokenValidTime, String secretKey) {
        return Jwts.builder()
                .setClaims(claims) //정보 저장
                .setIssuedAt(currentTime)  //토큰 발행시간 정보
                .setExpiration(new Date(currentTime.getTime() + tokenValidTime)) //Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  //암호화 알고리즘
                .compact();
    }

    //jwt 토큰 인증 정보 조회
    public Authentication getAuthentication(String token) {
        validationAuthorizationHeader(token);
        String available_token = extractToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(available_token));
        if(userDetails == null)
            return null;
        else
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //Header에서 token값을 가지고온다.
    public String getAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }

    //토큰의 유효성 검사
    public boolean validateToken(ServletRequest request, String jwtToken) {
        try {
            validationAuthorizationHeader(jwtToken);
            String token = extractToken(jwtToken);
            userDetailsService.loadUserByUsername(this.getUserPk(token));
            Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException | CustomException e) {
            log.info("ForbiddenException : {}", e.getMessage());
            request.setAttribute("exception", "ForbiddenException");
        } catch (MalformedJwtException e) {
            log.info("MalformedJwtException : {}", e.getMessage());
            request.setAttribute("exception", "MalformedJwtException");
        } catch (ExpiredJwtException e) {
            //토큰 만료시
            log.info("ExpiredJwtException : {}", e.getMessage());
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            log.info("UnsupportedJwtException : {}", e.getMessage());
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (IllegalArgumentException e) {
            log.info("IllegalArgumentException : {}", e.getMessage());
            request.setAttribute("exception", "IllegalArgumentException");
        }
        return false;
    }

    public boolean validateToken(String jwtToken) throws SignatureException, MalformedJwtException, ExpiredJwtException, UnsupportedJwtException, IllegalArgumentException {
        Jws<Claims> claims = Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(jwtToken);
        if(claims != null) {
            return !claims.getBody().getExpiration().before(new Date());
        }
        return false;
    }

    // RefreshToken 유효성 검증 메소드
    public String validateRefreshToken(RefreshToken refreshTokenObj) {
        String refreshToken = refreshTokenObj.getRefreshToken();

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(refreshToken);
            //AccessToken이 만료되지않았을떄만
            if(!claims.getBody().getExpiration().before(new Date())) {
                return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
            }
            return recreationAccessToken(claims.getBody().get("sub").toString(), claims.getBody().get("roles"));
        }catch (Exception e) {
            log.info("ValidateRefreshTokenException : {}", e.getMessage());
            throw new AuthenticationCustomException(AuthCode.ExpiredJwtException);
        }
        //토큰 만료시 login페이지 reDirect
    }

    //AccessToken 새로 발급
    private String recreationAccessToken(String email, Object roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        Date currentTime = new Date();
        return getToken(claims, currentTime, accessTokenValidTime, accessSecretKey);
    }

}
