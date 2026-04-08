package com.shortcutfightclubs.statement.domain.model.entity;

import com.shortcutfightclubs.statement.domain.model.enums.AttendanceStatus;
import com.shortcutfightclubs.statement.domain.model.vo.LessonId;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;

import java.time.Instant;
import java.util.Objects;

public class Attendance {

    private final String id;
    private final LessonId lessonId;
    private final UserId userId;
    private final AttendanceStatus status;
    private final Instant recordedAt;

    public Attendance(String id, LessonId lessonId, UserId userId, AttendanceStatus status, Instant recordedAt) {
        this.id = id;
        this.lessonId = Objects.requireNonNull(lessonId);
        this.userId = Objects.requireNonNull(userId);
        this.status = Objects.requireNonNull(status);
        this.recordedAt = Objects.requireNonNull(recordedAt);
    }

    public static Attendance record(LessonId lessonId, UserId userId) {
        return new Attendance(null, lessonId, userId, AttendanceStatus.PRESENT, Instant.now());
    }

    public String getId() {
        return id;
    }

    public LessonId getLessonId() {
        return lessonId;
    }

    public UserId getUserId() {
        return userId;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
