package com.shortcutfightclubs.statement.adapter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "grpc.server")
public class GrpcServerProperties {

    private int port = 9090;
}
