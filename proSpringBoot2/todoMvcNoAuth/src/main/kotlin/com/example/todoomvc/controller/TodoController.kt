package com.example.todoomvc.controller

import com.example.todoomvc.domain.Todo
import com.example.todoomvc.domain.TodoService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Controller
@RequestMapping("/todo")
class TodoController(private val todoService: TodoService) {
    @PostMapping("")
    @ResponseBody
    fun createTodo(@RequestBody payload: Todo, request: ServerHttpRequest, response: ServerHttpResponse) = todoService.create(payload)
            .flatMap {
                val location = UriComponentsBuilder
                        .fromHttpRequest(request)
                        .path("/{id}")
                        .buildAndExpand(it.id)
                        .toString()
                response.statusCode = HttpStatus.CREATED
                response.headers.set(HttpHeaders.LOCATION, location)
                Mono.empty<Void>()
            }

    @GetMapping("")
    fun getTodos(model: Model) = todoService.findAll()
            .buffer()
            .flatMap {
                model.addAttribute("todos", it)
                "mainPage".toMono()
            }
            .toMono()
            .defaultIfEmpty("mainPage")

    @GetMapping("/{id}")
    fun getTodo(@PathVariable id: String, model: Model) = todoService.findById(id)
            .flatMap {
                model.addAttribute("todo", it)
                "todoPage".toMono()
            }
            .defaultIfEmpty("todoPage")

    @PatchMapping("/{id}")
    @ResponseBody
    fun setCompleted(@PathVariable id: String, @RequestBody completed: Boolean) =
            todoService.setCompleted(id, completed)

    @DeleteMapping("/{id}")
    @ResponseBody
    fun deleteTodo(@PathVariable id: String) = todoService.deleteById(id)
}