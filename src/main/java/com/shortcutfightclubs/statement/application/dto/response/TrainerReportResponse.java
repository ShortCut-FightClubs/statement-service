package com.shortcutfightclubs.statement.application.dto.response;

public record TrainerReportResponse(
        int totalStudents,
        int totalLessons,
        double averageAttendanceRate,
        int totalContests
) {
}
