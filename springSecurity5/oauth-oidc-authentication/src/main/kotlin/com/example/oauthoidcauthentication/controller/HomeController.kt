package com.example.oauthoidcauthentication.controller

import java.security.Principal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
    @GetMapping("/Principal")
    fun homeOAuth2User(model: Model, principal: Principal): String {
        model.addAttribute("msg", "Welcome ${principal.name}")
        return "home"
    }

    @GetMapping("/OAuth2User")
    fun homeOAuth2User(model: Model, @AuthenticationPrincipal oauthUser: OAuth2User): String {
        model.addAttribute("msg", "Welcome ${oauthUser.attributes["email"]}")
        return "home"
    }

    @GetMapping("/OAuth2AuthorizedClient")
    fun homeOAuth2AuthorizedClient(
        model: Model,
        @RegisteredOAuth2AuthorizedClient oauthClient: OAuth2AuthorizedClient
    ): String {
        model.addAttribute("msg", "Welcome ${oauthClient.principalName}")
        return "home"
    }
}
