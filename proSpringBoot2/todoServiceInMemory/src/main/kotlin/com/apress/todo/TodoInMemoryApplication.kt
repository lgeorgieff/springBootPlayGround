package com.apress.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoInMemoryApplication

fun main(args: Array<String>) {
	runApplication<TodoInMemoryApplication>(*args)
}
