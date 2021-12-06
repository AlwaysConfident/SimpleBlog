package com.simpleblog.chen.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component // 用于让Spring自动检测，自动配置， 不使用代理类， 得到原始对象(源码产生的)
@WebFilter(urlPatterns = "/*") // 将类声明为过滤器
@Order(-99999) // 表示该Bean的执行顺序，值越小优先级越高
public class CorsFilter extends HttpFilter {

	private static final long serialVersionUID = 2386571986045107652L;
	private static final String OPTIONS_METHOD = "OPTIONS";

	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String origin = req.getHeader(HttpHeaders.ORIGIN); // 获取发出跨域请求的域名
		if (StringUtils.hasLength(origin)) {

			// 允许客户端的域
			res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);

			// 允许客户端提交的Header字段
			String requestHeaders = req.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
			if (StringUtils.hasLength(requestHeaders)) {
				res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders); // 允许发出请求携带的Header字段
			}

			// 允许客户端访问的Header字段
			res.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

			// 允许客户端携带凭证信息
			res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

			// 允许客户端请求方法
			res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");

			if (OPTIONS_METHOD.equalsIgnoreCase(req.getMethod())) { // 接受到浏览器的预检方法 OTIONS
				res.setStatus(HttpServletResponse.SC_NO_CONTENT); // 设置状态码204
				res.setContentType(MediaType.TEXT_HTML_VALUE); // 设置数据类型"text/html"
				res.setCharacterEncoding("utf-8");
				res.setContentLength(0);
				res.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1800");
				return;
			}
		}

		super.doFilter(req, res, chain);
	}
}