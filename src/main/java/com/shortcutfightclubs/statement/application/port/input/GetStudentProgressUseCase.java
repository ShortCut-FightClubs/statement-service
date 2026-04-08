package com.shortcutfightclubs.statement.application.port.input;

import com.shortcutfightclubs.statement.application.dto.response.StudentProgressResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GetStudentProgressUseCase {
    Mono<StudentProgressResponse> getProgress(UUID studentId);
}
