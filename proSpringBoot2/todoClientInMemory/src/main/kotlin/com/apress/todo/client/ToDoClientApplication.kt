package com.apress.todo.client

import com.apress.todo.client.model.ToDo
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.lang.Exception

@SpringBootApplication
class ToDoClientApplication {
	private val logger = LoggerFactory.getLogger(ToDoClientApplication::class.java)

	@Bean
	fun process(client: ToDoRestClient): CommandLineRunner {
		return CommandLineRunner {
			client.findAll().apply {
				assert(isEmpty())
				logger.info("findAll(): $this")
			}

			val newTodo = client.upsert(ToDo.create("A new ToDo"))!!
			logger.info("upsert(): $newTodo")

			client.findById(newTodo.id)!!.apply { logger.info("findById() $this") }

			client.setCompleted(newTodo.id)!!.apply {
				logger.info("setCompleted(): $this")
			}

			client.findAll().apply {
				assert(isEmpty())
				logger.info("findAll() $this")
			}

			client.delete(newTodo.id)
			try {
				client.findById(newTodo.id)
			} catch (err: Exception) {
				logger.info("findById(): $err")
			}
		}
	}
}

fun main(args: Array<String>) {
	SpringApplication(ToDoClientApplication::class.java)
			.apply {
				webApplicationType = WebApplicationType.NONE
				run(*args)
			}
}
