package com.shortcutfightclubs.statement.domain.model.entity;

import com.shortcutfightclubs.statement.domain.model.enums.AttendanceStatus;
import com.shortcutfightclubs.statement.domain.model.vo.LessonId;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceTest {

    @Test
    void record_shouldCreateAttendanceWithPresentStatus() {
        var lessonId = LessonId.generate();
        var userId = UserId.generate();

        var attendance = Attendance.record(lessonId, userId);

        assertThat(attendance.getId()).isNull();
        assertThat(attendance.getLessonId()).isEqualTo(lessonId);
        assertThat(attendance.getUserId()).isEqualTo(userId);
        assertThat(attendance.getStatus()).isEqualTo(AttendanceStatus.PRESENT);
        assertThat(attendance.getRecordedAt()).isNotNull();
    }

    @Test
    void equals_shouldBeBasedOnId() {
        var lessonId = LessonId.generate();
        var userId = UserId.generate();

        var a1 = new Attendance("id1", lessonId, userId, AttendanceStatus.PRESENT, java.time.Instant.now());
        var a2 = new Attendance("id1", lessonId, userId, AttendanceStatus.ABSENT, java.time.Instant.now());
        var a3 = new Attendance("id2", lessonId, userId, AttendanceStatus.PRESENT, java.time.Instant.now());

        assertThat(a1).isEqualTo(a2);
        assertThat(a1).isNotEqualTo(a3);
    }
}
