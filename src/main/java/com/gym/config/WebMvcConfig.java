package com.gym.config;

import com.gym.security.SessionAuthIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final SessionAuthIntercepter sessionAuthIntercepter;


    public WebMvcConfig(SessionAuthIntercepter sessionAuthIntercepter) {
        this.sessionAuthIntercepter = sessionAuthIntercepter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionAuthIntercepter)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/adminLogin", "/api/userLogin", "/api/logout") ;



    }
}
