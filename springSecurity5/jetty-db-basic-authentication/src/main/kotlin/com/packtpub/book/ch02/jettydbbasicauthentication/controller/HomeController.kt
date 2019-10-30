package com.packtpub.book.ch02.jettydbbasicauthentication.controller

import java.security.Principal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
    @GetMapping("/")
    fun home(model: Model, principal: Principal?): String {
        if (principal != null) model.addAttribute("msg", "Welcome ${principal.name}")
        return "home"
    }
}
