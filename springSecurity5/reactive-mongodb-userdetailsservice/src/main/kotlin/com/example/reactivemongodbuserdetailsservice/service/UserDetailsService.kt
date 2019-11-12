package com.example.reactivemongodbuserdetailsservice.service

import com.example.reactivemongodbuserdetailsservice.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserDetailsService : ReactiveUserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    override fun findByUsername(username: String) = userRepository
        .findById(username)
        .map { it.toAuthenticatedUser() as UserDetails }
        .switchIfEmpty(Mono.error(UsernameNotFoundException("username $username not found")))
}
