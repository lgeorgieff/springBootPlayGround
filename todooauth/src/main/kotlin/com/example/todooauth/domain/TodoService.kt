package com.example.todooauth.domain

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TodoService {
    fun findById(id: String): Mono<Todo>
    fun findAll(): Flux<Todo>
    fun setCompleted(id: String, value: Boolean): Mono<Todo>
    fun deleteById(id: String): Mono<Void>
    fun create(todo: Todo): Mono<Todo>
}