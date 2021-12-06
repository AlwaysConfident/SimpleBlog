package com.simpleblog.chen.controller;

import com.simpleblog.chen.pojo.User;
import com.simpleblog.chen.service.UserService;
import com.simpleblog.chen.utils.Response;
import com.simpleblog.chen.utils.ResponseFactory;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // 标记该类为一个控制类
public class LoginController {

  @Autowired
  UserService userService;

  @PostMapping(value = "/api/login") // 方法与路由映射
  @ResponseBody // 将方法返回的Java对象转换为JSON并直接写入到Response的Body中
  public Response login(@RequestBody User logintUser) {
    String userName = logintUser.getUserName();
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, logintUser.getPassword());
    usernamePasswordToken.setRememberMe(true);
    try {
      subject.login(usernamePasswordToken);
      return ResponseFactory.buildSucceResponse(userName);
    } catch (AuthenticationException e) {
      return ResponseFactory.buildFailResponse("账号密码错误");
    }
  }

  @PostMapping(value = "/api/regist")
  @ResponseBody
  public Response register(@RequestBody User registerUser) {
    String userName = registerUser.getUserName();
    String pwd = registerUser.getPassword();

    String salt = new SecureRandomNumberGenerator().nextBytes().toString(); // 生成盐，默认16位

    int times = 2; // hash迭代次数

    String encodePwd = new SimpleHash("md5", pwd, salt, times).toString(); // 生成加密密码

    registerUser.setUserName(userName);
    registerUser.setPassword(encodePwd);
    registerUser.setSalt(salt);

    userService.addUser(registerUser);

    return ResponseFactory.buildSucceResponse(userName);
  }

  @GetMapping(value = "/api/logout")
  @ResponseBody
  public Response logout() {
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    return ResponseFactory.buildSucceResponse("Logout");
  }

  @GetMapping(value = "/api/auth")
  @ResponseBody
  public Response authentication() { // 拦截验证服务器端的登录状态
    return ResponseFactory.buildSucceResponse("Authoriation");
  }

  @GetMapping(value = "/api/login")
  @ResponseBody
  public Response unauth() {
    return ResponseFactory.buildFailResponse("unauth");
  }
}
