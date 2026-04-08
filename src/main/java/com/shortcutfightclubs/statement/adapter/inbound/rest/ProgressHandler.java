package com.shortcutfightclubs.statement.adapter.inbound.rest;

import com.shortcutfightclubs.statement.application.port.input.GetStudentProgressUseCase;
import com.shortcutfightclubs.statement.application.port.input.GetTrainingStatsUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class ProgressHandler {

    private final GetStudentProgressUseCase getStudentProgressUseCase;
    private final GetTrainingStatsUseCase getTrainingStatsUseCase;

    public ProgressHandler(GetStudentProgressUseCase getStudentProgressUseCase,
                           GetTrainingStatsUseCase getTrainingStatsUseCase) {
        this.getStudentProgressUseCase = getStudentProgressUseCase;
        this.getTrainingStatsUseCase = getTrainingStatsUseCase;
    }

    public Mono<ServerResponse> getProgress(ServerRequest request) {
        var studentId = UUID.fromString(request.pathVariable("studentId"));
        return getStudentProgressUseCase.getProgress(studentId)
                .flatMap(progress -> ServerResponse.ok().bodyValue(progress));
    }

    public Mono<ServerResponse> getStats(ServerRequest request) {
        var userId = UUID.fromString(request.pathVariable("userId"));
        return getTrainingStatsUseCase.getStats(userId)
                .flatMap(stats -> ServerResponse.ok().bodyValue(stats));
    }
}
