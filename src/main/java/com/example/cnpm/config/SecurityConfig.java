package com.example.cnpm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class SecurityConfig {
    public class SercurityConfig implements WebMvcConfigurer {
        public void addViewController(ViewControllerRegistry registry){
            registry.addViewController("/student_homepage").setViewName("StudentHomePage");
            registry.addViewController("/index").setViewName("index");
            registry.addViewController("/login").setViewName("1");
            registry.addViewController("/logout").setViewName("1");
        }
    }
}

