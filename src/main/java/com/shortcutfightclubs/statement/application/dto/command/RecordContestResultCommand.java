package com.shortcutfightclubs.statement.application.dto.command;

import java.util.List;
import java.util.UUID;

public record RecordContestResultCommand(UUID contestId, List<UUID> participantIds) {
}
