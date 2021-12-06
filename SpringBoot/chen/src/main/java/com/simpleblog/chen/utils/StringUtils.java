package com.simpleblog.chen.utils;

import java.util.Random;

public class StringUtils { // 生成指定长度的随机字符串

  public static String generateRamdomString(int len) {
    String baseString = "abcdefghijklmnopqrstuvwxyz1234567890";
    Random random = new Random();
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < len; i++) {
      int number = random.nextInt(baseString.length());
      stringBuffer.append(baseString.charAt(number));
    }
    return stringBuffer.toString();
  }
}
