package com.example.springbootwebfluxsecurity.web

import com.example.springbootwebfluxsecurity.domain.Movie
import com.example.springbootwebfluxsecurity.domain.MovieRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class MovieHandler {
    @Autowired
    private lateinit var repository: MovieRepository

    fun listAllMovies(request: ServerRequest) = ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(repository.findAll(), Movie::class.java)

    fun getMovieById(request: ServerRequest) = repository
        .findById(request.pathVariable("id").toLong())
        .flatMap { ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(it), Movie::class.java) }
        .switchIfEmpty(ServerResponse.notFound().build())

    fun saveMovie(request: ServerRequest) = request
        .bodyToMono(Movie::class.java)
        .flatMap { ServerResponse.ok().body(repository.save(it), Movie::class.java) }
        .switchIfEmpty(ServerResponse.badRequest().build())

    fun deleteMovieById(request: ServerRequest) = repository
        .deleteById(request.pathVariable("id").toLong())
        .then(ServerResponse.ok().build())
}
