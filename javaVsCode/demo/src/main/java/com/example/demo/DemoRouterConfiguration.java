package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
class DemoRouterConfiguration {
    private DemoHandler mHandler;
    public DemoRouterConfiguration(DemoHandler handler) {
        mHandler = handler;
    }

    @Bean
    public RouterFunction<ServerResponse> helloRouter() {
        return route(GET("/ping/{message}"), mHandler::ping);
    }
}