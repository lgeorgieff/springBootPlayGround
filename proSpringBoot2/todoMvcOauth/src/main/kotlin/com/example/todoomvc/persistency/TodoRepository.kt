package com.example.todoomvc.persistency

import com.example.todoomvc.domain.Todo
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface TodoRepository : ReactiveCrudRepository<Todo, String>