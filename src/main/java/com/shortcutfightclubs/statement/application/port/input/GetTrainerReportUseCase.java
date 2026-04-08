package com.shortcutfightclubs.statement.application.port.input;

import com.shortcutfightclubs.statement.application.dto.response.TrainerReportResponse;
import reactor.core.publisher.Mono;

public interface GetTrainerReportUseCase {
    Mono<TrainerReportResponse> getTrainerReport();
}
