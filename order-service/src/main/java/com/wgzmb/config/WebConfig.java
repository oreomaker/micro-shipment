package com.wgzmb.config;

import com.wgzmb.component.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final List<String> WHITE_LIST = new ArrayList<>();
    static {
        WHITE_LIST.add("/user/login");
        WHITE_LIST.add("/user/register");
        WHITE_LIST.add("/user/info");
        WHITE_LIST.add("/user/logout");
        WHITE_LIST.add("/error");
        WHITE_LIST.add("/swagger-ui/*");
        WHITE_LIST.add("/swagger-resources/*");
        WHITE_LIST.add("/v3/api-docs/**");
    }

    // 跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                .allowedOrigins("*") // 设置允许跨域请求的域名
                .allowCredentials(true) // 是否允许cookies整数
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH") // 设置允许的请求方式
                .maxAge(3600 * 24); // 跨域允许时间
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new TokenInterceptor()) // 以下放行
                .excludePathPatterns(WHITE_LIST)
                .addPathPatterns("/**"); // 拦截所有，除了以上
    }
}
