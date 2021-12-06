package com.simpleblog.chen;

// import com.simpleblog.chen.interceptor.LoginInterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ChenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChenApplication.class, args);
	}

	// 使用Bean的方式而不是使用实现WebMvcConfigurationSupport接口来实现 WebMvcConfig ，避免重写后SpringBoot自身的WebMvcConfig自动装配失效
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			// @Bean
			// public LoginInterceptor getLoginIntercepter() {
			// 	return new LoginInterceptor();
			// }

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) { // 添加处理静态资源的句柄
				registry.addResourceHandler("/api/file/**")
						.addResourceLocations("file:" + "d:/Work/GitHub/SimpleBlog/Workspace/img/"); // 添加资源路径
			}

			// @Override
			// public void addInterceptors(InterceptorRegistry registry) {
			// 	registry.addInterceptor(getLoginIntercepter()).addPathPatterns("/**").excludePathPatterns("/index.html")
			// 			.excludePathPatterns("/api/login").excludePathPatterns("/api/logout")
			// 			.excludePathPatterns("/api/regist");
			// }
		};
	}
}
