package com.shortcutfightclubs.statement.application.port.output;

import com.shortcutfightclubs.statement.domain.model.entity.Attendance;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AttendanceRepository {
    Mono<Attendance> save(Attendance attendance);
    Flux<Attendance> findByUserId(UserId userId);
    Mono<Long> countByUserId(UserId userId);
    Mono<Long> countAll();
}
