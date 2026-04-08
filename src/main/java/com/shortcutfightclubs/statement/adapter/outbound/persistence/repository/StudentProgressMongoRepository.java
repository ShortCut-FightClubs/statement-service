package com.shortcutfightclubs.statement.adapter.outbound.persistence.repository;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.document.StudentProgressDocument;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface StudentProgressMongoRepository extends ReactiveCrudRepository<StudentProgressDocument, String> {
    Mono<StudentProgressDocument> findByUserId(String userId);
}
