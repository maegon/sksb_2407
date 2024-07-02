package com.example.sksb.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity  // 추가적인 보안이 이뤄지기 위한 어노테이션
@RequiredArgsConstructor
public class ApiSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/api/*/members/login", "/api/*/members/logout")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .csrf(
                        csrf -> csrf
                                .disable()
                )
                .cors(
                        cors -> cors.configure(http)
                )
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .disable()
                )
                .cors(cors -> cors
                        .configure(http)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}