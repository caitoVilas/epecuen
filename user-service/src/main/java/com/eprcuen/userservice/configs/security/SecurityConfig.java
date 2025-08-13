package com.eprcuen.userservice.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Security configuration for the user service.
 * Disables CSRF protection and allows all exchanges.
 * This is a basic configuration suitable for development or internal services.
 * For production, consider implementing proper authentication and authorization.
 *
 * @author caito
 *
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain filterChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exch ->
                        exch.anyExchange().permitAll())
                .build();
    }
}
