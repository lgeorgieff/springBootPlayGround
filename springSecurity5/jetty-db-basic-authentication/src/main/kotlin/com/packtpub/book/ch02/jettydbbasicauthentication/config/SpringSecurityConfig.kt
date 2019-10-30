package com.packtpub.book.ch02.jettydbbasicauthentication.config

import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
class SpringSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var dataSource: DataSource

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.jdbcAuthentication()?.dataSource(dataSource)
            ?.usersByUsernameQuery("select username, password, enabled from users where username = ?")
            ?.authoritiesByUsernameQuery("select username, authority from authorities where username= ?")
            ?.passwordEncoder(BCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()?.anyRequest()?.hasAnyRole("ADMIN", "USER")
            ?.and()?.httpBasic()
    }
}
