package com.example.springpostservice.post.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NOTFOUND(404, "일치하는 정보가 없습니다.", HttpStatus.NOT_FOUND),
    ForbiddenException(403, "해당 요청에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    NoSearchPostException(400, "게시물을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST)
    ;

    @Getter
    private int code;
    @Getter
    private String message;
    @Getter
    private HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String toString() {
        return "{" +
                "\"code\" : " + "\""+code+"\"" +
                "\"status\" : " + "\""+status+"\"" +
                "\"message\" : " + "\""+message+"\"" +
                "}";
    }
}
