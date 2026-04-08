package com.shortcutfightclubs.statement.application.dto.response;

import java.time.Instant;
import java.util.UUID;

public record AttendanceResponse(
        String id,
        UUID lessonId,
        UUID userId,
        String status,
        Instant recordedAt
) {
}
