package com.example.todooauth.persistency

import com.example.todooauth.domain.Todo
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TodoRepository : ReactiveCrudRepository<Todo, String>