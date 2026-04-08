package com.shortcutfightclubs.statement.domain.model.entity;

import com.shortcutfightclubs.statement.domain.model.vo.UserId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class StudentProgress {

    private final String id;
    private final UserId userId;
    private int lessonsAttended;
    private int contestsParticipated;
    private final List<String> achievements;
    private Instant lastUpdated;

    public StudentProgress(String id, UserId userId, int lessonsAttended, int contestsParticipated,
                           List<String> achievements, Instant lastUpdated) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId);
        this.lessonsAttended = lessonsAttended;
        this.contestsParticipated = contestsParticipated;
        this.achievements = new ArrayList<>(achievements != null ? achievements : List.of());
        this.lastUpdated = lastUpdated;
    }

    public static StudentProgress createNew(UserId userId) {
        return new StudentProgress(null, userId, 0, 0, List.of(), Instant.now());
    }

    public void incrementLessonsAttended() {
        this.lessonsAttended++;
        this.lastUpdated = Instant.now();
    }

    public void incrementContestsParticipated() {
        this.contestsParticipated++;
        this.lastUpdated = Instant.now();
    }

    public String getId() {
        return id;
    }

    public UserId getUserId() {
        return userId;
    }

    public int getLessonsAttended() {
        return lessonsAttended;
    }

    public int getContestsParticipated() {
        return contestsParticipated;
    }

    public List<String> getAchievements() {
        return Collections.unmodifiableList(achievements);
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentProgress that = (StudentProgress) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
