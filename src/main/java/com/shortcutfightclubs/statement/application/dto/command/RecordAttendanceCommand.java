package com.shortcutfightclubs.statement.application.dto.command;

import java.util.List;
import java.util.UUID;

public record RecordAttendanceCommand(UUID lessonId, List<UUID> participantIds) {
}
