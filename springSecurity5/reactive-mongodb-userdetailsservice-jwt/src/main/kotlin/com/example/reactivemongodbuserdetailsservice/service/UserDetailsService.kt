package com.example.reactivemongodbuserdetailsservice.service

import com.example.reactivemongodbuserdetailsservice.model.AuthenticatedUser
import com.example.reactivemongodbuserdetailsservice.model.User
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

    override fun findByUsername(email: String) = userRepository
        .findByEmail(email)
        .map { AuthenticatedUser(it) as UserDetails }
        .switchIfEmpty(Mono.error(UsernameNotFoundException("""username "$email" not found""")))

    fun getUser(email: String) = userRepository
        .findByEmail(email)
        .switchIfEmpty(Mono.error(UsernameNotFoundException("""username "$email" not found""")))

    fun createUser(user: User) = Mono
        .empty<User>()
        .flatMap {
            userRepository.findByEmail(user.email)
                .flatMap { Mono.error<User>(Error("""User "${user.email}" already exists""")) }
        }
        .switchIfEmpty(userRepository.save(user))

    fun deleteUser(email: String) = userRepository
        .findByEmail(email)
        .switchIfEmpty(Mono.error(UsernameNotFoundException("""username "$email" not found""")))

    fun getUsers() = userRepository.findAll()
}
