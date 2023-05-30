package com.example.springapigateway.security.handler;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationCustomException extends AuthenticationException {
    public AuthenticationCustomException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationCustomException(String msg) {
        super(msg);
    }

    public AuthenticationCustomException(AuthCode authCode) {
        super(authCode.toString());
    }
}
