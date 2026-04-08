package com.shortcutfightclubs.statement.domain.model.entity;

import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ContestResultTest {

    @Test
    void record_shouldCreateContestResult() {
        var contestId = UUID.randomUUID();
        var userId = UserId.generate();

        var result = ContestResult.record(contestId, userId);

        assertThat(result.getId()).isNull();
        assertThat(result.getContestId()).isEqualTo(contestId);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getPlacement()).isZero();
        assertThat(result.getScore()).isZero();
        assertThat(result.getRecordedAt()).isNotNull();
    }
}
