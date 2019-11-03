package com.example.springbootwebfluxsecurity

import com.example.springbootwebfluxsecurity.domain.Movie
import com.example.springbootwebfluxsecurity.domain.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringBootWebfluxSecurityApplication {
    @Autowired
    private lateinit var repository: MovieRepository

    @Bean
    fun init() = object : CommandLineRunner {
        override fun run(vararg args: String?) {
            repository
                .saveAll(listOf(
                    Movie(id = 0, title = "Casino Royale", genre = "Action"),
                    Movie(id = 1, title = "Quantum of Solace", genre = "Action"),
                    Movie(id = 2, title = "Skyfall", genre = "Action"),
                    Movie(id = 3, title = "Spectre", genre = "Action"),
                    Movie(id = 4, title = "No Time to Die", genre = "Action")
                ))
                .subscribe()
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBootWebfluxSecurityApplication>(*args)
}
