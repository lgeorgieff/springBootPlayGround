package com.example.reactivemongodbuserdetailsservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var securityContextRepository: SecurityContextRepository

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity) = http
        .csrf().disable()
        .authenticationManager(authenticationManager)
        .securityContextRepository(securityContextRepository)
        .authorizeExchange()
        .pathMatchers("/api/hello/", "/auth/").permitAll()
        .pathMatchers("/api/hello/admin", "/user/users").hasRole("ADMIN")
        .anyExchange().authenticated()
        .and().httpBasic()
        .and().formLogin().disable()
        .build()

    @Bean
    fun passwordEncoder() = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}
