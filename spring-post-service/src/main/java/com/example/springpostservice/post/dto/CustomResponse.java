package com.example.springpostservice.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class CustomResponse {
    private int code = HttpStatus.OK.value();
    private Object result;

    public CustomResponse() { }

    public void setResult(Object result) {
        this.result = result;
    }

    public static class ResponseMap extends CustomResponse {

        private Map responseData = new HashMap();

        public ResponseMap(int code, String message, Object object) {
            setResult(responseData);
            setCode(code);
            setResponseData(message, object);
        }

        public void setResponseData(String key, Object value) {
            this.responseData.put(key, value);
        }

        public void clear() {
            this.responseData.clear();
        }

    }
}