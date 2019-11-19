package com.example.reactiveoidcloginsinglepageapp

import com.example.reactiveoidcloginsinglepageapp.model.Profile
import com.example.reactiveoidcloginsinglepageapp.repository.ProfileRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Flux

@SpringBootApplication
class ReactiveOidcLoginSinglePageAppApplication {
    @Autowired
    private lateinit var logger: Logger

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @Bean
    fun logger() = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun commandLineRunner() = CommandLineRunner {
        profileRepository
            .deleteAll()
            .thenMany(
                Flux
                    .just("a", "b", "c", "d", "e")
                    .map { Profile(email = "$it@email.com") }
                    .flatMap(profileRepository::save)
            )
            .thenMany(profileRepository.findAll())
            .subscribe { logger.info("Created {}", it) }
    }
}

fun main(args: Array<String>) {
    runApplication<ReactiveOidcLoginSinglePageAppApplication>(*args)
}
