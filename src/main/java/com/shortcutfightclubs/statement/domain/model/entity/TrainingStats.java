package com.shortcutfightclubs.statement.domain.model.entity;

import com.shortcutfightclubs.statement.domain.model.vo.UserId;

import java.time.Instant;
import java.util.Objects;

public class TrainingStats {

    private final String id;
    private final UserId userId;
    private int totalLessons;
    private int attendedLessons;
    private double attendanceRate;
    private Instant lastUpdated;

    public TrainingStats(String id, UserId userId, int totalLessons, int attendedLessons,
                         double attendanceRate, Instant lastUpdated) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId);
        this.totalLessons = totalLessons;
        this.attendedLessons = attendedLessons;
        this.attendanceRate = attendanceRate;
        this.lastUpdated = lastUpdated;
    }

    public static TrainingStats createNew(UserId userId) {
        return new TrainingStats(null, userId, 0, 0, 0.0, Instant.now());
    }

    public void recalculate(int totalLessons, int attendedLessons) {
        this.totalLessons = totalLessons;
        this.attendedLessons = attendedLessons;
        this.attendanceRate = totalLessons > 0 ? (double) attendedLessons / totalLessons * 100.0 : 0.0;
        this.lastUpdated = Instant.now();
    }

    public String getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public int getTotalLessons() {
        return totalLessons;
    }

    public int getAttendedLessons() {
        return attendedLessons;
    }

    public double getAttendanceRate() {
        return attendanceRate;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingStats that = (TrainingStats) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
