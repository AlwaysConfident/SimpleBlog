package com.simpleblog.chen.filter;

import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simpleblog.chen.service.AdminPermissionService;
import com.simpleblog.chen.utils.SpringContextUtils;

import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import io.netty.handler.codec.http.HttpMethod;

// Shiro用于判断权限的URL路径过滤器
public class URLPathMatchingFilter extends PathMatchingFilter {
    @Autowired
    AdminPermissionService adminPermissionService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 放行预检的OPTIONS请求
        if (HttpMethod.OPTIONS.toString().equals((httpServletRequest).getMethod())) {
            httpServletResponse.setStatus(HttpStatus.SC_NO_CONTENT);
            return true;
        }

        // ShiroFilterFactoryBean与此类都是过滤器，都出现时默认的过滤器失效，因此不能声明为Bean给Spring管理
        // 因此无法使用Autowired注入，需要从应用上下文获取实例
        if (adminPermissionService == null) {
            adminPermissionService = SpringContextUtils.getContext().getBean(AdminPermissionService.class);
        }

        // 获取访问路径
        String requestAPI = getPathWithinApplication(request);

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            return false;
        }

        boolean needFilter = adminPermissionService.needFilter(requestAPI);
        if (!needFilter) {
            return true;
        } else {
            boolean hasPermission = false;
            String userName = subject.getPrincipal().toString();
            Set<String> permissionAPIs = adminPermissionService.listPermissionURLsByUser(userName);
            for (String api : permissionAPIs) {
                if (api.equals(requestAPI)) {
                    hasPermission = true;
                    break;
                }
            }
            if (hasPermission) {
                return true;
            } else {
                return false;
            }
        }
    }
}