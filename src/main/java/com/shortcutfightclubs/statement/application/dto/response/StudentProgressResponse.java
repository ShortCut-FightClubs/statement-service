package com.shortcutfightclubs.statement.application.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record StudentProgressResponse(
        String id,
        UUID userId,
        int lessonsAttended,
        int contestsParticipated,
        List<String> achievements,
        Instant lastUpdated
) {
}
