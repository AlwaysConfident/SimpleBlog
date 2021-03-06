package com.simpleblog.chen.config;

import java.util.Set;

import com.simpleblog.chen.pojo.User;
import com.simpleblog.chen.service.AdminPermissionService;
import com.simpleblog.chen.service.UserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

  @Autowired
  private UserService userService;

  @Autowired
  private AdminPermissionService adminPermissionService;

  // 简单重写获取授权信息方法
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    // 获取当前用户的所有权限
    String userName = principalCollection.getPrimaryPrincipal().toString();
    Set<String> permissions = adminPermissionService.listPermissionURLsByUser(userName);

    // 将权限放入授权信息中
    SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
    s.setStringPermissions(permissions);
    return s;
  }

  // 获取认证信息，即根据 token 中的用户名从数据库中获取密码、盐等并返回
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String userName = token.getPrincipal().toString();
    User user = userService.getUserByName(userName);
    String passwordInDB = user.getPassword();
    String salt = user.getSalt();
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, passwordInDB,
        ByteSource.Util.bytes(salt), getName());
    return authenticationInfo;
  }
}
