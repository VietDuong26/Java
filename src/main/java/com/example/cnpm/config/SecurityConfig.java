package com.example.cnpm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class SecurityConfig {
    public class SercurityConfig implements WebMvcConfigurer {
        public void addViewController(ViewControllerRegistry registry){
            registry.addViewController("/Student").setViewName("student");
            registry.addViewController("/login").setViewName("login");
            registry.addViewController("/logout").setViewName("login");
        }
    }
}

