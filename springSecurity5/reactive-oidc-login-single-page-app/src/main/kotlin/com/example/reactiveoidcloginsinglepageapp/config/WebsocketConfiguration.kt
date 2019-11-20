package com.example.reactiveoidcloginsinglepageapp.config

import com.example.reactiveoidcloginsinglepageapp.event.ProfileCreatedEvent
import com.example.reactiveoidcloginsinglepageapp.event.ProfileCreatedEventPublisher
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.util.concurrent.Executors
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Flux

@Configuration
class WebsocketConfiguration {
    @Bean
    fun executor() = Executors.newSingleThreadExecutor()

    @Bean
    fun handlerMapping(wsh: WebSocketHandler) = object : SimpleUrlHandlerMapping() {
        init {
            urlMap = mapOf("/ws/profiles" to wsh)
            order = 10
        }
    }

    @Bean
    fun webSocketHandlerAdapter() = WebSocketHandlerAdapter()

    @Bean
    fun webSocketHandler(objectMapper: ObjectMapper, eventPublisher: ProfileCreatedEventPublisher) = Flux
        .create<ProfileCreatedEvent>(eventPublisher)
        .share()
        .run {
            WebSocketHandler { session ->
                val webSocketMessage = map {
                    try {
                        objectMapper.writeValueAsString(it.source)
                    } catch (err: JsonProcessingException) {
                        throw Error(err)
                    }
                }
                .map { session.textMessage(it) }
                session.send(webSocketMessage)
            }
        }
}
