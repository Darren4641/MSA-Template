package com.example.springpostservice.post.handler;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(PostException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(PostException e) {

        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }
}
