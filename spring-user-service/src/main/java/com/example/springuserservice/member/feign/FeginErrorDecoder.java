package com.example.springuserservice.member.feign;

import com.example.springuserservice.member.handler.CustomException;
import com.example.springuserservice.member.handler.ErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeginErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                if(methodKey.contains("getPosts")) {
                    return new CustomException(ErrorCode.NOTFOUND);
                }
                break;
            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
