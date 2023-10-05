package com.example.cnpm.config;

import com.example.cnpm.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfig {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests)-> requests
                        .requestMatchers("/login/**","/","/regist/**","/insertStudent/**","/insertTeacher/**","/insertAdmin/**").permitAll()
                        .requestMatchers("/static/css/**",
                                                  "/static/js/**",
                                                  "/static/img/**").permitAll()
                        .requestMatchers("/Student/**").hasAnyRole("STUDENT","TEACHER","ADMIN")
                        .requestMatchers("/Teacher/**").hasAnyRole("ADMIN","TEACHER")
                        .requestMatchers("/Admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form->form.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/Student")
                        .permitAll())
                .logout((logout)->logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/index")
                        .permitAll());
        return http.build();
    }
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}

