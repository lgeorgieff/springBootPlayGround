package com.example.reactivemongodbuserdetailsservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity) = http
        .csrf().disable()
        .authorizeExchange()
        .pathMatchers("/").permitAll()
        .pathMatchers("/user").hasAnyRole("USER", "ADMIN")
        .pathMatchers("/admin").hasRole("ADMIN")
        .anyExchange().authenticated()
        .and().httpBasic()
        .and().formLogin().disable()
        .build()
}
