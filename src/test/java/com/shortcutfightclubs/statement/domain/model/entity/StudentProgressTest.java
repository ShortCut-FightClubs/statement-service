package com.shortcutfightclubs.statement.domain.model.entity;

import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentProgressTest {

    @Test
    void createNew_shouldInitializeWithZeros() {
        var userId = UserId.generate();
        var progress = StudentProgress.createNew(userId);

        assertThat(progress.getUserId()).isEqualTo(userId);
        assertThat(progress.getLessonsAttended()).isZero();
        assertThat(progress.getContestsParticipated()).isZero();
        assertThat(progress.getAchievements()).isEmpty();
        assertThat(progress.getLastUpdated()).isNotNull();
    }

    @Test
    void incrementLessonsAttended_shouldIncrementCounter() {
        var progress = StudentProgress.createNew(UserId.generate());

        progress.incrementLessonsAttended();
        progress.incrementLessonsAttended();

        assertThat(progress.getLessonsAttended()).isEqualTo(2);
    }

    @Test
    void incrementContestsParticipated_shouldIncrementCounter() {
        var progress = StudentProgress.createNew(UserId.generate());

        progress.incrementContestsParticipated();

        assertThat(progress.getContestsParticipated()).isEqualTo(1);
    }
}
