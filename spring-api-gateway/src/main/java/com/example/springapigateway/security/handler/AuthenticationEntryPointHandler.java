package com.example.springapigateway.security.handler;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");
        AuthCode errorCode;

        if(exception == null) {
            errorCode = AuthCode.UNAUTHORIZEDException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("NullPointerException")) {
            errorCode = AuthCode.UNAUTHORIZEDException;
            setResponse(response, errorCode);
            return;
        }


        if(exception.equals("PasswordNotFoundException")) {
            errorCode = AuthCode.PasswordNotFoundException;
            setResponse(response, errorCode);
            return;
        }

        if(exception.equals("ForbiddenException")) {
            errorCode = AuthCode.ForbiddenException;
            setResponse(response, errorCode);
            return;
        }

        //토큰이 만료된 경우
        if(exception.equals("ExpiredJwtException")) {
            errorCode = AuthCode.ExpiredJwtException;
            setResponse(response, errorCode);
            return;
        }

        //아이디 비밀번호가 다를 경우
        if(exception.equals("UsernameOrPasswordNotFoundException")) {
            errorCode = AuthCode.UsernameOrPasswordNotFoundException;
            setResponse(response, errorCode);
            return;
        }

    }

    private void setResponse(HttpServletResponse response, AuthCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        json.put("code", errorCode.getCode());
        json.put("message", errorCode.getMessage());
        response.getWriter().print(json);

    }
}
