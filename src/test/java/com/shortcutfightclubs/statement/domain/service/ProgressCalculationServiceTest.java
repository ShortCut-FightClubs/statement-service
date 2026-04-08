package com.shortcutfightclubs.statement.domain.service;

import com.shortcutfightclubs.statement.domain.model.entity.StudentProgress;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProgressCalculationServiceTest {

    private final ProgressCalculationService service = new ProgressCalculationService();

    @Test
    void updateForAttendance_shouldIncrementLessonsAttended() {
        var progress = StudentProgress.createNew(UserId.generate());

        var updated = service.updateForAttendance(progress);

        assertThat(updated.getLessonsAttended()).isEqualTo(1);
    }

    @Test
    void updateForContest_shouldIncrementContestsParticipated() {
        var progress = StudentProgress.createNew(UserId.generate());

        var updated = service.updateForContest(progress);

        assertThat(updated.getContestsParticipated()).isEqualTo(1);
    }

    @Test
    void initializeProgress_shouldCreateNewProgress() {
        var userId = UserId.generate();

        var progress = service.initializeProgress(userId);

        assertThat(progress.getUserId()).isEqualTo(userId);
        assertThat(progress.getLessonsAttended()).isZero();
    }
}
