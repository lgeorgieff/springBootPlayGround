package com.example.reactivemongodbuserdetailsservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository : ServerSecurityContextRepository {
    companion object {
        const val TOKEN_PREFIX = "Bearer "
    }

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw UnsupportedOperationException("Not supported yet")
    }

    override fun load(exchange: ServerWebExchange?) = if (exchange != null) {
        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (authHeader?.startsWith(TOKEN_PREFIX) == true) {
            authHeader.substring(TOKEN_PREFIX.length).run {
                authenticationManager.authenticate(UsernamePasswordAuthenticationToken(this, this))
                    .map { SecurityContextImpl(it) as SecurityContext }
            }
        } else {
            Mono.empty()
        }
    } else {
        Mono.empty()
    }
}
