package com.example.jettymembasicauthentication.config

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.inMemoryAuthentication()
            ?.withUser("user")?.password("{noop}user@password")?.roles("ADMIN", "USER")
            ?.and()?.withUser("admin")?.password("{noop}admin@password")?.roles("USER")
    }

    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()?.anyRequest()?.hasAnyRole("ADMIN", "USER")
            ?.and()?.httpBasic()
    }
}
