package com.packtpub.book.ch02.jettydbbasicauthentication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JettyDbBasicAuthenticationApplication

fun main(args: Array<String>) {
    runApplication<JettyDbBasicAuthenticationApplication>(*args)
}
