package com.shortcutfightclubs.statement.application.port.output;

import com.shortcutfightclubs.statement.domain.model.entity.StudentProgress;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentProgressRepository {
    Mono<StudentProgress> save(StudentProgress progress);
    Mono<StudentProgress> findByUserId(UserId userId);
    Flux<StudentProgress> findAll();
    Mono<Long> countAll();
}
