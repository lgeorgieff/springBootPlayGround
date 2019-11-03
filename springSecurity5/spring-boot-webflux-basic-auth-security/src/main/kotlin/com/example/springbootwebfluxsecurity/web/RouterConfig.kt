package com.example.springbootwebfluxsecurity.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class RouterConfig {
    @Bean
    fun movieRouter(movieHandler: MovieHandler) =
        router {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/api/movie").invoke(movieHandler::listAllMovies)
                GET("/api/movie/{id}").invoke(movieHandler::getMovieById)
                POST("/api/movie").invoke(movieHandler::saveMovie)
                DELETE("/api/movie/{id}").invoke(movieHandler::deleteMovieById)
            }
        }
}
