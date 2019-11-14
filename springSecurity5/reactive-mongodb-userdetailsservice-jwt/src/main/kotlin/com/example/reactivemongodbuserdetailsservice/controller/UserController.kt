package com.example.reactivemongodbuserdetailsservice.controller

import com.example.reactivemongodbuserdetailsservice.model.AuthenticatedUser
import com.example.reactivemongodbuserdetailsservice.model.User
import com.example.reactivemongodbuserdetailsservice.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var userService: UserDetailsService

    @PostMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@RequestBody user: Mono<User>) = user.flatMap {
        userService.createUser(it.copy(password = passwordEncoder.encode(it.password)))
    }

    @GetMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUsers() = userService.getUsers()

    @GetMapping("/{username}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUser(@AuthenticationPrincipal user: Mono<AuthenticatedUser>, @PathVariable username: String) = user
        .flatMap {
            if (it.username == username) userService.getUser(username)
            else Mono.empty()
        }

    @DeleteMapping("/{username}")
    fun deleteUser(@AuthenticationPrincipal user: Mono<AuthenticatedUser>, @PathVariable username: String) = user
        .flatMap {
            if (it.username == username) userService.deleteUser(username)
            else Mono.empty()
        }
}
