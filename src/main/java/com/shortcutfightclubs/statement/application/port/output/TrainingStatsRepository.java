package com.shortcutfightclubs.statement.application.port.output;

import com.shortcutfightclubs.statement.domain.model.entity.TrainingStats;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TrainingStatsRepository {
    Mono<TrainingStats> save(TrainingStats stats);
    Mono<TrainingStats> findByUserId(UserId userId);
    Flux<TrainingStats> findAll();
}
