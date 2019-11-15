package com.example.reactivemongodbuserdetailsservice.controller

import com.example.reactivemongodbuserdetailsservice.jwt.generateToken
import com.example.reactivemongodbuserdetailsservice.model.AuthenticatedUser
import com.example.reactivemongodbuserdetailsservice.model.LoginRequest
import com.example.reactivemongodbuserdetailsservice.model.LoginResponse
import com.example.reactivemongodbuserdetailsservice.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
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
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var userService: UserDetailsService

    @PostMapping("/")
    fun login(@RequestBody loginRequest: Mono<LoginRequest>) = loginRequest
        .flatMap { (username: String, password: String) ->
            userService
                .findByUsername(username)
                .filter { passwordEncoder.matches(password, it.password) }
        }
        .onErrorResume { Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, it.message)) }
        .map { LoginResponse(token = generateToken(it as AuthenticatedUser)) }
        .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad username or password")))
}
