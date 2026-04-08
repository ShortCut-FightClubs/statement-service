package com.shortcutfightclubs.statement.adapter.outbound.persistence.repository;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.document.AttendanceDocument;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AttendanceMongoRepository extends ReactiveCrudRepository<AttendanceDocument, String> {
    Flux<AttendanceDocument> findByUserId(String userId);
    Mono<Long> countByUserId(String userId);
}
