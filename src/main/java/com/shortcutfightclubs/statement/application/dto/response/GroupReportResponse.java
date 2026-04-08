package com.shortcutfightclubs.statement.application.dto.response;

import java.util.List;

public record GroupReportResponse(
        int totalStudents,
        double averageAttendanceRate,
        List<StudentProgressResponse> students
) {
}
