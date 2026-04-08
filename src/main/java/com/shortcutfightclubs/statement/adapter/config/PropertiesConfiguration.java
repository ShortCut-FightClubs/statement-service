package com.shortcutfightclubs.statement.adapter.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        GrpcServerProperties.class
})
public class PropertiesConfiguration {
}
