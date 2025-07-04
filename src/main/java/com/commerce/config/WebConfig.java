package com.commerce.config;

import com.commerce.intercepter.ResultVOInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${file.resource-locations}") // 파일 경로
    private String resourceLocations;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://127.0.0.1:3000","http://localhost:3000","http://mandoo1027.tplinkdns.com:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResultVOInterceptor())
                .addPathPatterns("/admin/service/**") // 인터셉터를 적용할 경로 설정
                .excludePathPatterns("/admin/service/HCO0101S01" //로그인
                                        ,"/admin/service/HCO0101S02"  // OPT 인증
                                        ,"/admin/service/HCO0101S03" // 로그아웃
                                        ,"/admin/service/MNU0201S02" // 메뉴 조회
                                        , "/admin/service/otp/**");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:"+resourceLocations);
        // Windows라면 "file:///C:/uploads/"
    }
}