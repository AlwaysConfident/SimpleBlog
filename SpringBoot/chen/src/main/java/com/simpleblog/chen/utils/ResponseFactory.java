package com.simpleblog.chen.utils;

public class ResponseFactory {
    public static Response buildResponse(int responseCode, String message, Object data) {
        return new Response(responseCode, message, data);
    }

    public static Response buildResponse(ResponseCode responseCode, String message, Object data) {
        return buildResponse(responseCode.code, message, data);
    }

    public static Response buildFailResponse(String message) {
        return buildResponse(ResponseCode.FAIL, message, null);
    }

    public static Response buildSucceResponse(Object data) {
        return buildResponse(ResponseCode.SUCCESS, "success", data);
    }
}
