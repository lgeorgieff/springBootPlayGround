package com.example.reactivemongodbuserdetailsservice.controller

import com.example.reactivemongodbuserdetailsservice.model.SimpleMessage
import java.security.Principal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HelloController {
    @GetMapping("/")
    fun getHelloRoot() = Mono.just(SimpleMessage(value = "Hello from root!"))

    @GetMapping("/user")
    fun getHelloUser(principal: Mono<Principal>) = principal.map { SimpleMessage(value = "Hello ${it.name}!") }

    @GetMapping("/admin")
    fun getHelloAdmin(principal: Mono<Principal>) = principal.map { SimpleMessage(value = "Hello ${it.name}!") }
}
