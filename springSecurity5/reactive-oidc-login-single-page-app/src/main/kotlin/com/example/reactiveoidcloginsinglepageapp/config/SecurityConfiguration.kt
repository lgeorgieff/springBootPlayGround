package com.example.reactiveoidcloginsinglepageapp.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity

@EnableWebFluxSecurity
class SecurityConfiguration {
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity) = http
        .authorizeExchange()
        .anyExchange().authenticated()
        .and()
        .oauth2Login()
        .and()
        .oauth2ResourceServer()
        .jwt().and().and().build()
}
