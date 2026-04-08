package com.shortcutfightclubs.statement.application.dto.response;

import java.time.Instant;
import java.util.UUID;

public record TrainingStatsResponse(
        String id,
        UUID userId,
        int totalLessons,
        int attendedLessons,
        double attendanceRate,
        Instant lastUpdated
) {
}
