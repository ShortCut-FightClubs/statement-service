package com.shortcutfightclubs.statement.domain.service;

import com.shortcutfightclubs.statement.domain.model.entity.StudentProgress;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;

public class ProgressCalculationService {

    public StudentProgress updateForAttendance(StudentProgress progress) {
        progress.incrementLessonsAttended();
        return progress;
    }

    public StudentProgress updateForContest(StudentProgress progress) {
        progress.incrementContestsParticipated();
        return progress;
    }

    public StudentProgress initializeProgress(UserId userId) {
        return StudentProgress.createNew(userId);
    }
}
