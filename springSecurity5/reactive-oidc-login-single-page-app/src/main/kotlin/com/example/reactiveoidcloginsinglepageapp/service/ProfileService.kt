package com.example.reactiveoidcloginsinglepageapp.service

import com.example.reactiveoidcloginsinglepageapp.event.ProfileCreatedEvent
import com.example.reactiveoidcloginsinglepageapp.model.Profile
import com.example.reactiveoidcloginsinglepageapp.repository.ProfileRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProfileService {
    @Autowired
    private lateinit var publisher: ApplicationEventPublisher

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    fun getAll() = profileRepository.findAll()

    fun get(email: String) = profileRepository
        .findByEmail(email.toLowerCase())
        .switchIfEmpty(Mono.error(ProfileServiceError("No profile for $email found")))

    fun update(currentEmail: String, newEmail: String) = profileRepository
        .findByEmail(currentEmail.toLowerCase())
        .switchIfEmpty(Mono.error(ProfileServiceError("No profile for $currentEmail found")))
        .flatMap { profileRepository.save(it.copy(email = newEmail.toLowerCase())) }

    fun delete(email: String) = profileRepository
        .findByEmail(email.toLowerCase())
        .switchIfEmpty(Mono.error(ProfileServiceError("No profile for $email found")))
        .flatMap { profileRepository.deleteById(it.id) }

    fun create(email: String) = profileRepository
        .findByEmail(email.toLowerCase())
        .flatMap { Mono.error<Profile>(ProfileServiceError("Profile with $email already exists")) }
        .switchIfEmpty(profileRepository.save(Profile(email = email.toLowerCase())))
        .doOnSuccess { publisher.publishEvent(
            ProfileCreatedEvent(
                source = it
            )
        ) }

    class ProfileServiceError(message: String? = null, cause: Throwable? = null) : Error(message, cause)
}
