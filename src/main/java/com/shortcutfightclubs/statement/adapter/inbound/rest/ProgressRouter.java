package com.shortcutfightclubs.statement.adapter.inbound.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProgressRouter {

    @Bean
    public RouterFunction<ServerResponse> progressRoutes(ProgressHandler handler) {
        return RouterFunctions.route()
                .GET("/api/v1/progress/{studentId}", handler::getProgress)
                .GET("/api/v1/stats/{userId}", handler::getStats)
                .build();
    }
}
