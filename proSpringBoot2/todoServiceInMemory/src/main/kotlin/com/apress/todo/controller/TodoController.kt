package com.apress.todo.controller

import com.apress.todo.model.CommonRepository
import com.apress.todo.model.ToDo
import com.apress.todo.validation.ToDoValidationError
import com.apress.todo.validation.ValidationErrorBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/api")
class TodoController(private val repository: CommonRepository<ToDo>) {

    @GetMapping("/todo")
    fun getToDos() = ResponseEntity.ok(repository.findAll())

    @GetMapping("/todo/{id}")
    fun getToDoById(@PathVariable id: String): ResponseEntity<ToDo> {
        val todo = repository.tryFindById(id)
        return if (todo != null) ResponseEntity.ok(todo) else ResponseEntity.notFound().build()
    }

    @PatchMapping("/todo/{id}")
    fun setCompleted(@PathVariable id: String): ResponseEntity<ToDo> {
        val completedTodo = ToDo(repository.findById(id), true)
        repository.save(completedTodo)
        val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(completedTodo.id)
                .toUri()
        return ResponseEntity
                .ok()
                .header("Location", location.toString())
                .build()
    }

    @RequestMapping("/todo", method = [RequestMethod.POST, RequestMethod.PUT])
    fun createToDo(@RequestBody todo: ToDo, errors: Errors): ResponseEntity<*> {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ValidationErrorBuilder.fromBindingErrors(errors))
        }

        val savedTodo = repository.save(todo)
        val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTodo.id)
                .toUri()
        return ResponseEntity
                .created(location)
                .build<ToDo>()
    }

    @DeleteMapping("/todo/{id}")
    fun deleteToDo(@PathVariable id: String): ResponseEntity<ToDo> {
        repository.delete(repository.findById(id))
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/todo")
    fun deleteToDo(@RequestBody todo: ToDo): ResponseEntity<ToDo> {
        repository.delete(todo)
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(exception: Exception) = ToDoValidationError(exception.message ?: "")
}
