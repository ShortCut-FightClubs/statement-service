package com.shortcutfightclubs.statement.application.port.input;

import com.shortcutfightclubs.statement.application.dto.command.RecordAttendanceCommand;
import reactor.core.publisher.Mono;

public interface RecordAttendanceUseCase {
    Mono<Boolean> execute(RecordAttendanceCommand command);
}
