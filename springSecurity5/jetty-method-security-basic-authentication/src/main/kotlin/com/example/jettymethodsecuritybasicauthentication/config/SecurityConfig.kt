package com.example.jettymethodsecuritybasicauthentication.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.inMemoryAuthentication()
            ?.withUser("user")?.password("{noop}user@password")?.roles("ADMIN", "USER")
            ?.and()?.withUser("admin")?.password("{noop}admin@password")?.roles("USER")
    }

    override fun configure(http: HttpSecurity?) {
        http?.httpBasic()
    }
}
