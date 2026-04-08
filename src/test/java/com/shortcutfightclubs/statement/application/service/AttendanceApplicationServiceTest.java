package com.shortcutfightclubs.statement.application.service;

import com.shortcutfightclubs.statement.application.dto.command.RecordAttendanceCommand;
import com.shortcutfightclubs.statement.application.port.output.AttendanceRepository;
import com.shortcutfightclubs.statement.application.port.output.StudentProgressRepository;
import com.shortcutfightclubs.statement.application.port.output.TrainingStatsRepository;
import com.shortcutfightclubs.statement.domain.model.entity.Attendance;
import com.shortcutfightclubs.statement.domain.model.entity.StudentProgress;
import com.shortcutfightclubs.statement.domain.model.entity.TrainingStats;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import com.shortcutfightclubs.statement.domain.service.ProgressCalculationService;
import com.shortcutfightclubs.statement.domain.service.StatsAggregationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttendanceApplicationServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private StudentProgressRepository progressRepository;
    @Mock
    private TrainingStatsRepository statsRepository;
    @Mock
    private ProgressCalculationService progressCalculationService;
    @Mock
    private StatsAggregationService statsAggregationService;

    @InjectMocks
    private AttendanceApplicationService service;

    @Test
    void execute_shouldRecordAttendanceAndUpdateProgressAndStats() {
        var userId = UUID.randomUUID();
        var command = new RecordAttendanceCommand(UUID.randomUUID(), List.of(userId));
        var progress = StudentProgress.createNew(UserId.from(userId));
        var stats = TrainingStats.createNew(UserId.from(userId));

        when(attendanceRepository.save(any(Attendance.class)))
                .thenReturn(Mono.just(Attendance.record(
                        com.shortcutfightclubs.statement.domain.model.vo.LessonId.generate(),
                        UserId.from(userId))));
        when(progressRepository.findByUserId(any(UserId.class)))
                .thenReturn(Mono.just(progress));
        when(progressCalculationService.updateForAttendance(any()))
                .thenReturn(progress);
        when(progressRepository.save(any(StudentProgress.class)))
                .thenReturn(Mono.just(progress));
        when(attendanceRepository.countByUserId(any(UserId.class)))
                .thenReturn(Mono.just(1L));
        when(attendanceRepository.countAll())
                .thenReturn(Mono.just(1L));
        when(statsRepository.findByUserId(any(UserId.class)))
                .thenReturn(Mono.just(stats));
        when(statsAggregationService.recalculate(any(), any(int.class), any(int.class)))
                .thenReturn(stats);
        when(statsRepository.save(any(TrainingStats.class)))
                .thenReturn(Mono.just(stats));

        StepVerifier.create(service.execute(command))
                .expectNext(true)
                .verifyComplete();
    }
}
