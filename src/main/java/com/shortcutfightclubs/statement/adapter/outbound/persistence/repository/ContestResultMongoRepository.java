package com.shortcutfightclubs.statement.adapter.outbound.persistence.repository;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.document.ContestResultDocument;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContestResultMongoRepository extends ReactiveCrudRepository<ContestResultDocument, String> {
    Flux<ContestResultDocument> findByUserId(String userId);
    Mono<Long> countByUserId(String userId);
}
