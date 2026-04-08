package com.shortcutfightclubs.statement.application.port.input;

import com.shortcutfightclubs.statement.application.dto.response.TrainingStatsResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GetTrainingStatsUseCase {
    Mono<TrainingStatsResponse> getStats(UUID userId);
}
