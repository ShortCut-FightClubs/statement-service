package com.shortcutfightclubs.statement.adapter.inbound.grpc;

import io.grpc.Server;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class GrpcServerLifecycle {

    private final Server server;

    public GrpcServerLifecycle(Server server) {
        this.server = server;
    }

    @PostConstruct
    public void start() throws IOException {
        server.start();
        log.info("gRPC server started on port {}", server.getPort());
    }

    @PreDestroy
    public void stop() {
        if (server != null) {
            server.shutdown();
            log.info("gRPC server stopped");
        }
    }
}
