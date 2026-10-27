package com.hulkhiretech.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.hulkhiretech.payment.security.HmacFilter;
import com.hulkhiretech.payment.service.interfaces.HmacSHA256Service;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final HmacSHA256Service hmacSHA256Service;

    public SecurityConfig(HmacSHA256Service hmacSHA256Service) {
        this.hmacSHA256Service = hmacSHA256Service;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(crd -> crd.disable())

                .addFilterAfter(new HmacFilter(hmacSHA256Service), LogoutFilter.class)

                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}