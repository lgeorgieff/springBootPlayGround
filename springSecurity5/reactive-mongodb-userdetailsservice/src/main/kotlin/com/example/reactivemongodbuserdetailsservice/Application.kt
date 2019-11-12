package com.example.reactivemongodbuserdetailsservice

import com.example.reactivemongodbuserdetailsservice.repositories.User
import com.example.reactivemongodbuserdetailsservice.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class JwtSampleApplication {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Bean
    fun commandLineRunner() = object : CommandLineRunner {
        override fun run(vararg args: String?) {
            userRepository.save(User("user", "{noop}user@password", "ROLE_USER"))
                .and(userRepository.save(User("admin", "{noop}admin@password", "ROLE_ADMIN")))
                .block()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<JwtSampleApplication>(*args)
}
