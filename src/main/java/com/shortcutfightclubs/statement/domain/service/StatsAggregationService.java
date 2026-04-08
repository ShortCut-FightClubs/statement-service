package com.shortcutfightclubs.statement.domain.service;

import com.shortcutfightclubs.statement.domain.model.entity.TrainingStats;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;

public class StatsAggregationService {

    public TrainingStats recalculate(TrainingStats stats, int totalLessons, int attendedLessons) {
        stats.recalculate(totalLessons, attendedLessons);
        return stats;
    }

    public TrainingStats initializeStats(UserId userId) {
        return TrainingStats.createNew(userId);
    }
}
