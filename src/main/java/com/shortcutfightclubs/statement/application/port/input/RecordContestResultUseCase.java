package com.shortcutfightclubs.statement.application.port.input;

import com.shortcutfightclubs.statement.application.dto.command.RecordContestResultCommand;
import reactor.core.publisher.Mono;

public interface RecordContestResultUseCase {
    Mono<Boolean> execute(RecordContestResultCommand command);
}
