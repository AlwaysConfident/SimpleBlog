package com.simpleblog.chen.utils;

public enum ResponseCode {
  SUCCESS(200),
  FAIL(400),
  FORBIDDEN(401),
  NOT_FOUND(400),
  INTERNAL_SERVER_ERROR(500);

  public int code;

  ResponseCode(int code) {
    this.code = code;
  }
}
