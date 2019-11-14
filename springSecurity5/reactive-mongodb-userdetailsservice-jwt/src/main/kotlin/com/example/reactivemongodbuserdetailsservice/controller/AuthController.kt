package com.example.reactivemongodbuserdetailsservice.controller

import com.example.reactivemongodbuserdetailsservice.model.LoginRequest
import com.example.reactivemongodbuserdetailsservice.model.LoginResponse
import com.example.reactivemongodbuserdetailsservice.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
class AuthController {
    @Autowired
    private lateinit var userService: UserDetailsService

    @PostMapping("/")
    fun login(@RequestBody loginRequest: Mono<LoginRequest>) = loginRequest
        .flatMap { (username: String, password: String) ->
            userService
                .findByUsername(username)
                // .filter { it.password == password } // TODO: implement
        }
        .onErrorResume { Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, it.message)) }
        .map { LoginResponse(token = "abc...token...xyz") }
}
