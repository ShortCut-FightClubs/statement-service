package com.shortcutfightclubs.statement.application.port.input;

import com.shortcutfightclubs.statement.application.dto.response.GroupReportResponse;
import reactor.core.publisher.Mono;

public interface GetGroupReportUseCase {
    Mono<GroupReportResponse> getGroupReport();
}
