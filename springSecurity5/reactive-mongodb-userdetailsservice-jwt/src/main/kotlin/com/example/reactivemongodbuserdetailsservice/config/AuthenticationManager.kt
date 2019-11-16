package com.example.reactivemongodbuserdetailsservice.config

import com.example.reactivemongodbuserdetailsservice.jwt.AUTHORITIES_KEY
import com.example.reactivemongodbuserdetailsservice.jwt.getAllClaimsFromToken
import com.example.reactivemongodbuserdetailsservice.jwt.getUsernameFromToken
import com.example.reactivemongodbuserdetailsservice.jwt.isTokenExpired
import com.example.reactivemongodbuserdetailsservice.model.AuthenticatedUser
import com.example.reactivemongodbuserdetailsservice.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager : ReactiveAuthenticationManager {
    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Suppress("UNCHECKED_CAST")
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        val token = authentication?.credentials?.toString() ?: return Mono.empty()
        return try { getUsernameFromToken(token) } catch (err: Exception) { null }
            ?.let { username ->
                if (!isTokenExpired(token)) {
                    userDetailsService
                        .findByUsername(username)
                        .map { it as AuthenticatedUser }
                        .map { userFromRepository ->
                            val authorities = (getAllClaimsFromToken(token)[AUTHORITIES_KEY] as
                                Collection<Map<String, String>>)
                                .mapNotNull { it["authority"] }
                                .map(::SimpleGrantedAuthority)

                            val auth = UsernamePasswordAuthenticationToken(
                                AuthenticatedUser(
                                    username = username,
                                    password = userFromRepository.password,
                                    lastName = userFromRepository.lastName,
                                    firstName = userFromRepository.firstName,
                                    authorities = authorities
                                ),
                                null,
                                authorities
                            )

                            SecurityContextHolder.getContext().authentication = auth
                            auth as Authentication
                        }
                } else {
                    Mono.empty()
                }
            } ?: Mono.empty()
    }
}
