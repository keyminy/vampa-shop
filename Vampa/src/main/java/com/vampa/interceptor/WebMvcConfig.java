package com.vampa.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/member/login.do"); // 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
        
        registry.addInterceptor(new AdminInterceptor())
        		.addPathPatterns("/admin/**"); // 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
        
        registry.addInterceptor(new CartInterceptor())
        .addPathPatterns("/cart/**"); // 해당 경로에 접근하기 전에 인터셉터가 가로챈다.
    }
}
