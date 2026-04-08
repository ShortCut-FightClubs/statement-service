package com.shortcutfightclubs.statement.adapter.inbound.grpc;

import com.shortcutfightclubs.statement.adapter.config.GrpcServerProperties;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    @Bean
    public Server grpcServer(GrpcServerProperties properties,
                             StatementGrpcService statementGrpcService) {
        return ServerBuilder.forPort(properties.getPort())
                .addService(statementGrpcService)
                .build();
    }
}
