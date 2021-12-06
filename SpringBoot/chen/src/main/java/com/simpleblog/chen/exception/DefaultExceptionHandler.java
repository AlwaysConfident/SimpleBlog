package com.simpleblog.chen.exception;

import com.simpleblog.chen.utils.Response;
import com.simpleblog.chen.utils.ResponseFactory;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleAuthorizationException(UnauthorizedException e) {
        String message = "权限认证失败";
        return ResponseFactory.buildFailResponse(message);
    }
}
