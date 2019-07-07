package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.time.LocalDateTime;

import reactor.core.publisher.Mono;

@Component
class DemoHandler {
    public Mono<ServerResponse> ping(ServerRequest request) {
        String message = request.pathVariable("message");
        return ok().body(Mono.just(LocalDateTime.now() + ": " + message), String.class);
    }
}