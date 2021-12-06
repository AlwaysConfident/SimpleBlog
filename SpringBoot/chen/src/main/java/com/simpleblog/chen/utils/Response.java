package com.simpleblog.chen.utils;

import lombok.Data;

@Data
public class Response {

    private int responseCode;
    private String message;
    private Object res;

    public Response(int responseCode, String message, Object data) {
        this.responseCode = responseCode;
        this.message = message;
        this.res = data;
    }
}
