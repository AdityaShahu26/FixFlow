package com.fixflow.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception{

        http

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/v1/users/register")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/users/register")
                        .permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();

    }


}
