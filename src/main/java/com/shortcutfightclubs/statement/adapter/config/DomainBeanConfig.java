package com.shortcutfightclubs.statement.adapter.config;

import com.shortcutfightclubs.statement.domain.service.ProgressCalculationService;
import com.shortcutfightclubs.statement.domain.service.StatsAggregationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeanConfig {

    @Bean
    public ProgressCalculationService progressCalculationService() {
        return new ProgressCalculationService();
    }

    @Bean
    public StatsAggregationService statsAggregationService() {
        return new StatsAggregationService();
    }
}
