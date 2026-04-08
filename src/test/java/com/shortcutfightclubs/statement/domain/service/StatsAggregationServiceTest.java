package com.shortcutfightclubs.statement.domain.service;

import com.shortcutfightclubs.statement.domain.model.entity.TrainingStats;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class StatsAggregationServiceTest {

    private final StatsAggregationService service = new StatsAggregationService();

    @Test
    void recalculate_shouldUpdateStats() {
        var stats = TrainingStats.createNew(UserId.generate());

        var updated = service.recalculate(stats, 10, 8);

        assertThat(updated.getTotalLessons()).isEqualTo(10);
        assertThat(updated.getAttendedLessons()).isEqualTo(8);
        assertThat(updated.getAttendanceRate()).isCloseTo(80.0, within(0.01));
    }

    @Test
    void recalculate_shouldHandleZeroLessons() {
        var stats = TrainingStats.createNew(UserId.generate());

        var updated = service.recalculate(stats, 0, 0);

        assertThat(updated.getAttendanceRate()).isEqualTo(0.0);
    }

    @Test
    void initializeStats_shouldCreateNewStats() {
        var userId = UserId.generate();

        var stats = service.initializeStats(userId);

        assertThat(stats.getUserId()).isEqualTo(userId);
        assertThat(stats.getTotalLessons()).isZero();
    }
}
