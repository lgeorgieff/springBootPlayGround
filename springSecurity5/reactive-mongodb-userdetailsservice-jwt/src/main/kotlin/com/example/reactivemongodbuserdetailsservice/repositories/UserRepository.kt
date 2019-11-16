package com.example.reactivemongodbuserdetailsservice.repositories

import com.example.reactivemongodbuserdetailsservice.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<User, String> {
    fun findByEmail(email: String): Mono<User>
    fun deleteByEmail(email: String): Mono<Void>
}
