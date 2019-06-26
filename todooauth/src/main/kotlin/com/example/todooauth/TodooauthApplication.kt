package com.example.todooauth

import com.example.todooauth.domain.Todo
import com.example.todooauth.domain.TodoService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Flux
import reactor.core.publisher.toMono

@SpringBootApplication
class TodooauthApplication(private val todoService: TodoService) {
	@Bean
	fun process() = CommandLineRunner {
		Flux.just(
				Todo(description = "Learn Spring"),
				Todo(description = "Learn Spring Boot"),
				Todo(description = "Learn Thrift"),
				Todo(description = "Learn docker", completed = true),
				Todo(description = "Learn docker-compose", completed = true))
				.flatMap { todoService.create(it) }
				.flatMap { println("Created: $it").toMono() }
				.subscribe()
	}
}

fun main(args: Array<String>) {
	runApplication<TodooauthApplication>(*args)
}
