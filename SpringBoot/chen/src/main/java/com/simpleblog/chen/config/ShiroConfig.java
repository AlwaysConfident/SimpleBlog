package com.simpleblog.chen.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import com.simpleblog.chen.filter.URLPathMatchingFilter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

  @Bean // 用于管理Shiro Bean 生命周期
  public static LifecycleBeanPostProcessor getLifecycleBeanProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  @Bean // 创建拦截url的过滤器
  public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    shiroFilterFactoryBean.setLoginUrl("/api/login"); // 设置拦截未登录请求时重定向的路径

    // 定义对于哪个路径应用哪个过滤器
    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
    
    // 定义过滤器的名字
    Map<String, Filter> customizedFilter = new HashMap<String, Filter>();

    // 设置自定义过滤器名称为url
    customizedFilter.put("url", getURLPathMatchingFilter());

    // 对管理接口的访问启用自定义拦截 (路径， 过滤器)，即执行URLPahtMatchingFilter中定义的过滤方法
    filterChainDefinitionMap.put("/api/admin/**", "url");

    // 启用自定义过滤器
    shiroFilterFactoryBean.setFilters(customizedFilter);

    // 使用shiro自带的authc过滤器，防止前端直接构造输入命令访问
    filterChainDefinitionMap.put("/api/auth", "authc");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }

  @Bean // 用于管理安全相关操作
  public SecurityManager securityManager(CookieRememberMeManager rememberMeManager) {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(getMyRealm());
    securityManager.setRememberMeManager(rememberMeManager);
    return securityManager;
  }

  @Bean // 用于获取和加工数据给 SecurityManager
  public MyRealm getMyRealm() {
    MyRealm myRealm = new MyRealm();
    myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
    return myRealm;
  }

  @Bean // 用于生成登录Token
  public HashedCredentialsMatcher hashedCredentialsMatcher() {
    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    hashedCredentialsMatcher.setHashAlgorithmName("md5");
    hashedCredentialsMatcher.setHashIterations(2);
    return hashedCredentialsMatcher;
  }

  @Bean // 开启Shiro的AOP注解支持
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }

  @Bean // 管理记录subject身份的信息
  public CookieRememberMeManager rememberMeManager() {
    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
    cookieRememberMeManager.setCookie(rememberMeCookie()); // 设置Cookie模板
    cookieRememberMeManager.setCipherKey("ALEXALEXALEXALEX".getBytes()); // 设置加密解密的盐，16位(与Shiro中限制的key长度有关)
    return cookieRememberMeManager;
  }

  @Bean // 默认的Cookie实现类
  public SimpleCookie rememberMeCookie() {
    SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
    simpleCookie.setMaxAge(259200);
    return simpleCookie;
  }

  public URLPathMatchingFilter getURLPathMatchingFilter() {
    return new URLPathMatchingFilter();
  }
}
