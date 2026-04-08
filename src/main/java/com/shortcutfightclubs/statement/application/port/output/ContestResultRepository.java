package com.shortcutfightclubs.statement.application.port.output;

import com.shortcutfightclubs.statement.domain.model.entity.ContestResult;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContestResultRepository {
    Mono<ContestResult> save(ContestResult result);
    Flux<ContestResult> findByUserId(UserId userId);
    Mono<Long> countByUserId(UserId userId);
    Mono<Long> countAll();
}
