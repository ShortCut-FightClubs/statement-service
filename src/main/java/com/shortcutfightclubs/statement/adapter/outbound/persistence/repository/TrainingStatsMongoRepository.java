package com.shortcutfightclubs.statement.adapter.outbound.persistence.repository;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.document.TrainingStatsDocument;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TrainingStatsMongoRepository extends ReactiveCrudRepository<TrainingStatsDocument, String> {
    Mono<TrainingStatsDocument> findByUserId(String userId);
}
