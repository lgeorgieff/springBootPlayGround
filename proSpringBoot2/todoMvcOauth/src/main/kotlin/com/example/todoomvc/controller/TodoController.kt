package com.example.todoomvc.controller

import com.example.todoomvc.domain.Todo
import com.example.todoomvc.domain.TodoService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.security.Principal

@Controller
@RequestMapping("/todo")
class TodoController(private val todoService: TodoService) {
    @PostMapping("")
    @ResponseBody
    fun createTodo(@RequestBody payload: Todo, request: ServerHttpRequest, response: ServerHttpResponse) = todoService.create(payload)
            .flatMap {
                val location = UriComponentsBuilder
                        .fromHttpRequest(request)
                        .path("/{id}")
                        .buildAndExpand(it.id)
                        .toString()
                response.statusCode = HttpStatus.CREATED
                response.headers.set(HttpHeaders.LOCATION, location)
                Mono.empty<Void>()
            }

    @GetMapping("")
    fun getTodos(@AuthenticationPrincipal oauth2User: Mono<OAuth2User>,
                 @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
                 principal: Principal,
                 model: Model)
            = oauth2User
            .flatMap {
                println(">>> oauth2User.attributes ${it.attributes}")
                println(">>> oauth2User.authorities ${it.authorities}")

                authorizedClient.apply {
                    println(">>> authorizedClient.accessToken $accessToken")
                    println(">>> authorizedClient.refreshToken $refreshToken")
                    println(">>> authorizedClient.principalName $principalName")
                    println(">>> authorizedClient.clientRegistration.clientName: ${clientRegistration.clientName}")
                    println(">>> authorizedClient.clientRegistration.clientId: ${clientRegistration.clientId}")
                    println(">>> authorizedClient.clientRegistration.clientSecret: ${clientRegistration.clientSecret}")
                    println(">>> authorizedClient.clientRegistration.providerDetails.userInfoEndpoint.uri: " +
                            clientRegistration.providerDetails.userInfoEndpoint.uri)
                }

                println(">>> principal.name: ${principal.name}")

                val userInfoEndpointUri = authorizedClient.clientRegistration.providerDetails.userInfoEndpoint.uri
                if (userInfoEndpointUri.isNotEmpty()) {
                    val auth2Credentials = ExchangeFilterFunction.ofRequestProcessor { clientRequest ->
                        ClientRequest.from(clientRequest)
                                .header(HttpHeaders.AUTHORIZATION,
                                        "Bearer " + authorizedClient.accessToken.tokenValue)
                                .build()
                                .toMono()
                    }

                    WebClient
                            .builder()
                            .filter(auth2Credentials)
                            .build()
                            .get()
                            .uri(userInfoEndpointUri)
                            .retrieve()
                            .bodyToMono(Map::class.java)
                            .flatMap { userInfo ->
                                println(">>> UserInfoEndpoint: $userInfo")
                                Mono.empty<Void>()
                            }
                } else {
                    Mono.empty<Void>()
                }
            }
            .thenMany(todoService.findAll())
            .buffer()
            .flatMap {
                model.addAttribute("todos", it)
                "mainPage".toMono()
            }
            .toMono()
            .defaultIfEmpty("mainPage")

    @GetMapping("/{id}")
    fun getTodo(@PathVariable id: String, model: Model) = todoService.findById(id)
            .flatMap {
                model.addAttribute("todo", it)
                "todoPage".toMono()
            }
            .defaultIfEmpty("todoPage")

    @PatchMapping("/{id}")
    @ResponseBody
    fun setCompleted(@PathVariable id: String, @RequestBody completed: Boolean) =
            todoService.setCompleted(id, completed)

    @DeleteMapping("/{id}")
    @ResponseBody
    fun deleteTodo(@PathVariable id: String) = todoService.deleteById(id)
}