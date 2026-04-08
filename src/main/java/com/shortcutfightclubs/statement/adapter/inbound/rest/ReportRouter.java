package com.shortcutfightclubs.statement.adapter.inbound.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ReportRouter {

    @Bean
    public RouterFunction<ServerResponse> reportRoutes(ReportHandler handler) {
        return RouterFunctions.route()
                .GET("/api/v1/reports/student/{studentId}", handler::getStudentReport)
                .GET("/api/v1/reports/group", handler::getGroupReport)
                .GET("/api/v1/reports/trainer", handler::getTrainerReport)
                .build();
    }
}
