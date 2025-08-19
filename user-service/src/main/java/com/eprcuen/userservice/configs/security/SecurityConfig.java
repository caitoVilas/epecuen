package com.eprcuen.userservice.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    /**
     * Configures the security filter chain for the application.
     * Disables CSRF protection and allows all exchanges.
     *
     * @param http the ServerHttpSecurity object to configure
     * @return a SecurityWebFilterChain that applies the specified security settings
     */
    @Bean
    SecurityWebFilterChain filterChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exch ->
                        exch.anyExchange().permitAll())
                .build();
    }

    /**
     * Provides a PasswordEncoder bean for encoding passwords.
     * Uses BCryptPasswordEncoder for secure password hashing.
     *
     * @return a PasswordEncoder instance
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
