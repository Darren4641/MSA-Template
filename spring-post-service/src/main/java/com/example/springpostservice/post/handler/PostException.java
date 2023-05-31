package com.example.springpostservice.post.handler;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostException extends RuntimeException{
    ErrorCode errorCode;
}
