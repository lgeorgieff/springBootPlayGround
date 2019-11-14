package com.example.reactivemongodbuserdetailsservice.controller

import com.example.reactivemongodbuserdetailsservice.model.AuthenticatedUser
import com.example.reactivemongodbuserdetailsservice.model.SimpleMessage
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/")
class HelloController {
    @GetMapping("/")
    fun getHelloRoot() = Mono.just(SimpleMessage(value = "Hello from root!"))

    @GetMapping("/user")
    fun getHelloUser(@AuthenticationPrincipal user: Mono<AuthenticatedUser>) = user
        .map { SimpleMessage(value = "Hello ${it.firstName} ${it.lastName}!") }

    @GetMapping("/admin")
    fun getHelloAdmin(@AuthenticationPrincipal user: Mono<AuthenticatedUser>) = user
        .map { SimpleMessage(value = "Hello ${it.firstName} ${it.lastName}!") }
}
