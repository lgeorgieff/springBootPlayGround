package com.example.reactiveerrorhandling.controller

import com.example.reactiveerrorhandling.model.HelloError
import com.example.reactiveerrorhandling.model.SimpleMessage
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestController
class MainController {
    private val logger = LoggerFactory.getLogger(MainController::class.java)

    @GetMapping("/hello/unhandled-error/{name}")
    fun getHelloUnhandledError(@PathVariable name: String) = Mono
        .just(SimpleMessage(value = "Hello $name!"))
        .flatMap { Mono.error<SimpleMessage<String>>(HelloError("Some error")) }

    @GetMapping("/hello/doOnError/{name}")
    fun getHelloDoOnError(@PathVariable name: String) = Mono
        .just(SimpleMessage(value = "Hello $name!"))
        .flatMap { Mono.error<SimpleMessage<String>>(HelloError("Some error")) }
        .doOnError {
            logger.error("GET /hello/doOnError/{name} -> {}", it.toString())
            Mono.just(SimpleMessage(value = "new value"))
        }

    @GetMapping("/hello/onErrorReturn/{name}")
    fun getHelloOnErrorReturn(@PathVariable name: String) = Mono
        .just(SimpleMessage(value = "Hello $name!"))
        .flatMap { Mono.error<SimpleMessage<String>>(HelloError("Some error")) }
        .onErrorReturn(Throwable::class.java, SimpleMessage(value = "new value"))

    @GetMapping("/hello/onErrorResume/{name}")
    fun getHelloOnErrorResume(@PathVariable name: String) = Mono
        .just(SimpleMessage(value = "Hello $name!"))
        .flatMap { Mono.error<SimpleMessage<String>>(HelloError("Some error")) }
        .onErrorResume {
            logger.error("GET /hello/onErrorResume/{name} -> {}", it.toString())
            Mono.just(SimpleMessage(value = "new value"))
        }

    @GetMapping("/hello/ResponseStatusException/{name}")
    fun getHelloResponseStatusException(@PathVariable name: String) = Mono
        .just(SimpleMessage(value = "Hello $name!"))
        .flatMap { Mono.error<SimpleMessage<String>>(HelloError("Some error")) }
        .onErrorResume {
            logger.error("GET /hello/ResponseStatusException/{name} -> {}", it.toString())
            Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, it.message, it))
        }
}
