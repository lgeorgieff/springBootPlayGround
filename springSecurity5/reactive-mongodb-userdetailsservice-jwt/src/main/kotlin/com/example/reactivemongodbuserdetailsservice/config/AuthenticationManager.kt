package com.example.reactivemongodbuserdetailsservice.config

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
