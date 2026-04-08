package com.shortcutfightclubs.statement.adapter.inbound.rest;

import com.shortcutfightclubs.statement.domain.exception.DomainException;
import com.shortcutfightclubs.statement.domain.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.webflux.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof DomainException domainEx) {
            return writeProblemDetail(exchange, domainEx.getErrorCode(), domainEx.getMessage());
        }

        log.error("Unexpected error", ex);
        return writeProblemDetail(exchange, ErrorCode.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
    }

    private Mono<Void> writeProblemDetail(ServerWebExchange exchange, ErrorCode errorCode, String detail) {
        var response = exchange.getResponse();
        response.setStatusCode(errorCode.getHttpStatus());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        var body = """
                {"type":"about:blank","title":"%s","status":%d,"detail":"%s","code":"%s"}"""
                .formatted(
                        errorCode.getMessage(),
                        errorCode.getHttpStatus().value(),
                        detail,
                        errorCode.getFullCode()
                );

        var buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
