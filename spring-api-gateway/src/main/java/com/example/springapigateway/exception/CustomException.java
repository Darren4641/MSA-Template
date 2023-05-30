package com.example.springapigateway.exception;

import com.example.springapigateway.security.handler.AuthCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    AuthCode authCode;
}
