package com.example.reactivemongodbuserdetailsservice.repositories

import com.example.reactivemongodbuserdetailsservice.model.AuthenticatedUser
import org.springframework.data.annotation.Id
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority

interface UserRepository : ReactiveCrudRepository<User, String>

data class User(@Id val username: String, val password: String, val role: String) {
    fun toAuthenticatedUser() = AuthenticatedUser(username, password, listOf(SimpleGrantedAuthority(role)))
}
