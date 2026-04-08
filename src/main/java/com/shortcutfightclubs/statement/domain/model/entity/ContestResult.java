package com.shortcutfightclubs.statement.domain.model.entity;

import com.shortcutfightclubs.statement.domain.model.vo.UserId;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class ContestResult {

    private final String id;
    private final UUID contestId;
    private final UserId userId;
    private final int placement;
    private final int score;
    private final Instant recordedAt;

    public ContestResult(String id, UUID contestId, UserId userId, int placement, int score, Instant recordedAt) {
        this.id = id;
        this.contestId = Objects.requireNonNull(contestId);
        this.userId = Objects.requireNonNull(userId);
        this.placement = placement;
        this.score = score;
        this.recordedAt = Objects.requireNonNull(recordedAt);
    }

    public static ContestResult record(UUID contestId, UserId userId) {
        return new ContestResult(null, contestId, userId, 0, 0, Instant.now());
    }

    public String getId() {
        return id;
    }

    public UUID getContestId() {
        return contestId;
    }

    public UserId getUserId() {
        return userId;
    }

    public int getPlacement() {
        return placement;
    }

    public int getScore() {
        return score;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContestResult that = (ContestResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
