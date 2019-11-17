package com.example.oidclogin.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class MainController {
    @GetMapping("/")
    fun getHello(@AuthenticationPrincipal oauth2User: Mono<OAuth2User>) = oauth2User
        .map { obj: OAuth2User -> "Hello ${obj.name}" }
        .switchIfEmpty(Mono.just("Hello John Doe!"))
}
