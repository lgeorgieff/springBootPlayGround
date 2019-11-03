package com.example.springbootwebfluxsecurity.config

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User

@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun springWebFilterChain(http: ServerHttpSecurity) = http
        .authorizeExchange()
        .pathMatchers(HttpMethod.GET, "/api/movie/**").hasAnyRole("USER", "ADMIN")
        .pathMatchers(HttpMethod.POST, "/api/movie/**").hasRole("ADMIN")
        .pathMatchers(HttpMethod.DELETE, "/api/movie/**").hasRole("ADMIN")
        .anyExchange().authenticated()
        .and().csrf().disable()
        .httpBasic()
        .and().build()

    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService {
        val user = User.withUsername("user").password("{noop}user@password")
            .roles("USER").build()
        val admin = User.withUsername("admin").password("{noop}admin@password")
            .roles("ADMIN", "USER").build()
        return MapReactiveUserDetailsService(user, admin)
    }
}
