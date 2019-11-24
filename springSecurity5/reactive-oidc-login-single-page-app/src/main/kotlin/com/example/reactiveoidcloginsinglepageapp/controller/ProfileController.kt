package com.example.reactiveoidcloginsinglepageapp.controller

import com.example.reactiveoidcloginsinglepageapp.service.ProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class ProfileController {
    @Autowired
    private lateinit var profileService: ProfileService

    @GetMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllProfiles() = profileService.getAll()

    @GetMapping("/{email}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getProfileByEmail(@PathVariable email: String) = profileService.get(email)

    @DeleteMapping("/{email}")
    fun deleteProfileByEmail(@PathVariable email: String) = profileService.delete(email)

    @PostMapping("/", consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createProfile(@RequestBody email: Mono<String>) = email.flatMap { profileService.create(it) }

    @PutMapping("/{email}", consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateProfile(@PathVariable(name = "email") currentEmail: String, @RequestBody newEmail: Mono<String>) = newEmail
        .flatMap { profileService.update(currentEmail, it) }
}
