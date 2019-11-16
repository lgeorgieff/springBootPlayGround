package com.example.reactivemongodbuserdetailsservice

import com.example.reactivemongodbuserdetailsservice.model.User
import com.example.reactivemongodbuserdetailsservice.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class JwtSampleApplication {
    @Autowired
    private lateinit var userService: UserDetailsService

    @Bean
    fun commandLineRunner() = CommandLineRunner {
        val user = User(
            email = "user@email.com",
            password = "{noop}user@password",
            authorities = listOf("ROLE_USER"),
            firstName = "Jane",
            lastName = "Doe"
        )
        val admin = User(
            email = "admin@email.com",
            password = "{noop}admin@password",
            authorities = listOf("ROLE_USER", "ROLE_ADMIN"),
            firstName = "John",
            lastName = "Doe"
        )

        userService.createUser(user)
            .then(userService.createUser(admin))
            .block()
    }
}

fun main(args: Array<String>) {
    runApplication<JwtSampleApplication>(*args)
}
