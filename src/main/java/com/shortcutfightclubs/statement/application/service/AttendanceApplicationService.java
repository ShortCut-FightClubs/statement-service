package com.shortcutfightclubs.statement.application.service;

import com.shortcutfightclubs.statement.application.dto.command.RecordAttendanceCommand;
import com.shortcutfightclubs.statement.application.port.input.RecordAttendanceUseCase;
import com.shortcutfightclubs.statement.application.port.output.AttendanceRepository;
import com.shortcutfightclubs.statement.application.port.output.StudentProgressRepository;
import com.shortcutfightclubs.statement.application.port.output.TrainingStatsRepository;
import com.shortcutfightclubs.statement.domain.model.entity.Attendance;
import com.shortcutfightclubs.statement.domain.model.vo.LessonId;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import com.shortcutfightclubs.statement.domain.service.ProgressCalculationService;
import com.shortcutfightclubs.statement.domain.service.StatsAggregationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AttendanceApplicationService implements RecordAttendanceUseCase {

    private final AttendanceRepository attendanceRepository;
    private final StudentProgressRepository progressRepository;
    private final TrainingStatsRepository statsRepository;
    private final ProgressCalculationService progressCalculationService;
    private final StatsAggregationService statsAggregationService;

    public AttendanceApplicationService(AttendanceRepository attendanceRepository,
                                        StudentProgressRepository progressRepository,
                                        TrainingStatsRepository statsRepository,
                                        ProgressCalculationService progressCalculationService,
                                        StatsAggregationService statsAggregationService) {
        this.attendanceRepository = attendanceRepository;
        this.progressRepository = progressRepository;
        this.statsRepository = statsRepository;
        this.progressCalculationService = progressCalculationService;
        this.statsAggregationService = statsAggregationService;
    }

    @Override
    public Mono<Boolean> execute(RecordAttendanceCommand command) {
        var lessonId = LessonId.from(command.lessonId());

        return Flux.fromIterable(command.participantIds())
                .flatMap(participantId -> {
                    var userId = UserId.from(participantId);
                    var attendance = Attendance.record(lessonId, userId);

                    return attendanceRepository.save(attendance)
                            .then(updateProgress(userId))
                            .then(updateStats(userId));
                })
                .then(Mono.just(true));
    }

    private Mono<Void> updateProgress(UserId userId) {
        return progressRepository.findByUserId(userId)
                .defaultIfEmpty(progressCalculationService.initializeProgress(userId))
                .map(progressCalculationService::updateForAttendance)
                .flatMap(progressRepository::save)
                .then();
    }

    private Mono<Void> updateStats(UserId userId) {
        return attendanceRepository.countByUserId(userId)
                .flatMap(attended -> attendanceRepository.countAll()
                        .flatMap(total -> statsRepository.findByUserId(userId)
                                .defaultIfEmpty(statsAggregationService.initializeStats(userId))
                                .map(stats -> statsAggregationService.recalculate(stats, total.intValue(), attended.intValue()))
                                .flatMap(statsRepository::save)))
                .then();
    }
}
