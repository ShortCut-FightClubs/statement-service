package com.shortcutfightclubs.statement.application.dto.response;

import java.time.Instant;
import java.util.UUID;

public record ContestResultResponse(
        String id,
        UUID contestId,
        UUID userId,
        int placement,
        int score,
        Instant recordedAt
) {
}
