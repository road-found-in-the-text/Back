package com.example.Back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/api/v1/script/**",
                        "/api/v1/assessment-question/**",
                        "/api/v1/member/**",
                        "/api/v1/auth/**"
                ).authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .cors().configurationSource(request -> {
                    CorsConfiguration cors = new CorsConfiguration();
                    cors.setAllowedOrigins(Arrays.asList(
                            "https://www.thelearningmate.com",
                            "http://localhost:3000"
                    ));
                    cors.setAllowedMethods(Arrays.asList("*"));
                    cors.setAllowedHeaders(Arrays.asList("*"));
                    return cors;
                });
    }
}
