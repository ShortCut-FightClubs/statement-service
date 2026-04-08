package com.shortcutfightclubs.statement.application.dto.response;

import java.util.List;
import java.util.UUID;

public record StudentReportResponse(
        UUID userId,
        int lessonsAttended,
        int contestsParticipated,
        double attendanceRate,
        List<String> achievements,
        List<ContestResultResponse> contestResults
) {
}
