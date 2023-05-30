package com.example.springapigateway.exception;

import com.example.springapigateway.security.handler.AuthCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponseEntity {
    private int code;
    private String message;
    private HttpStatus status;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(AuthCode e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseEntity.builder()
                        .status(e.getStatus())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }
}
