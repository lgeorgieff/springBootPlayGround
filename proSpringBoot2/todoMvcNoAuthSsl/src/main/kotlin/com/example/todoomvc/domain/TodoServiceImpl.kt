package com.example.todoomvc.domain

import com.example.todoomvc.persistency.TodoRepository
import java.time.LocalDateTime
import org.springframework.stereotype.Service

@Service
class TodoServiceImpl(private val todoRepository: TodoRepository) : TodoService {
    override fun findById(id: String) = todoRepository.findById(id)

    override fun findAll() = todoRepository.findAll()

    override fun setCompleted(id: String, value: Boolean) = findById(id)
            .flatMap {
                todoRepository.save(it.copy(completed = value, modified = LocalDateTime.now()))
            }

    override fun deleteById(id: String) = todoRepository.deleteById(id)

    override fun create(todo: Todo) = todoRepository.save(todo)
}
