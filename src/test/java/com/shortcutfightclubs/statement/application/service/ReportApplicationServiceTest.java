package com.shortcutfightclubs.statement.application.service;

import com.shortcutfightclubs.statement.application.port.output.AttendanceRepository;
import com.shortcutfightclubs.statement.application.port.output.ContestResultRepository;
import com.shortcutfightclubs.statement.application.port.output.StudentProgressRepository;
import com.shortcutfightclubs.statement.application.port.output.TrainingStatsRepository;
import com.shortcutfightclubs.statement.domain.exception.StudentProgressNotFoundException;
import com.shortcutfightclubs.statement.domain.model.entity.StudentProgress;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportApplicationServiceTest {

    @Mock
    private StudentProgressRepository progressRepository;
    @Mock
    private TrainingStatsRepository statsRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private ContestResultRepository contestResultRepository;

    @InjectMocks
    private ReportApplicationService service;

    @Test
    void getProgress_shouldReturnProgress() {
        var userId = UUID.randomUUID();
        var progress = new StudentProgress("id1", UserId.from(userId), 5, 2, List.of(), java.time.Instant.now());

        when(progressRepository.findByUserId(any(UserId.class)))
                .thenReturn(Mono.just(progress));

        StepVerifier.create(service.getProgress(userId))
                .assertNext(response -> {
                    assertThat(response.userId()).isEqualTo(userId);
                    assertThat(response.lessonsAttended()).isEqualTo(5);
                    assertThat(response.contestsParticipated()).isEqualTo(2);
                })
                .verifyComplete();
    }

    @Test
    void getProgress_shouldThrowWhenNotFound() {
        var userId = UUID.randomUUID();
        when(progressRepository.findByUserId(any(UserId.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.getProgress(userId))
                .expectError(StudentProgressNotFoundException.class)
                .verify();
    }
}
