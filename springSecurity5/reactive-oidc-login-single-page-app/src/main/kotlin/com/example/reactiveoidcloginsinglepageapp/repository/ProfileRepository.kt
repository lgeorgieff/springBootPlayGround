package com.example.reactiveoidcloginsinglepageapp.repository

import com.example.reactiveoidcloginsinglepageapp.model.Profile
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface ProfileRepository : ReactiveCrudRepository<Profile, String> {
    fun findByEmail(email: String): Mono<Profile>
}
