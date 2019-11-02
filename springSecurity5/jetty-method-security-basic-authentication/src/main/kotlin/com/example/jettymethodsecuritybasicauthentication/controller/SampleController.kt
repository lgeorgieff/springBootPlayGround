package com.example.jettymethodsecuritybasicauthentication.controller

import com.example.jettymethodsecuritybasicauthentication.domain.SimpleResponse
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SampleController {
    @GetMapping("/hello/{name}")
    @Secured("ROLE_USER")
    fun hello(@PathVariable name: String) = SimpleResponse("Hello $name!")
}