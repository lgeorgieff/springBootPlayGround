package com.example.todoomvc

import com.example.todoomvc.domain.Todo
import com.example.todoomvc.domain.TodoService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Flux
import reactor.core.publisher.toMono

@SpringBootApplication
class TodoApplication(private val todoService: TodoService) {
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
	runApplication<TodoApplication>(*args)
}
