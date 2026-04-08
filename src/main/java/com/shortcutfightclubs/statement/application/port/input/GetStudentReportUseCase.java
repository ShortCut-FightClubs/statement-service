package com.shortcutfightclubs.statement.application.port.input;

import com.shortcutfightclubs.statement.application.dto.response.StudentReportResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GetStudentReportUseCase {
    Mono<StudentReportResponse> getStudentReport(UUID studentId);
}
