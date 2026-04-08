package com.shortcutfightclubs.statement.application.service;

import com.shortcutfightclubs.statement.application.dto.command.RecordContestResultCommand;
import com.shortcutfightclubs.statement.application.port.output.ContestResultRepository;
import com.shortcutfightclubs.statement.application.port.output.StudentProgressRepository;
import com.shortcutfightclubs.statement.domain.model.entity.ContestResult;
import com.shortcutfightclubs.statement.domain.model.entity.StudentProgress;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import com.shortcutfightclubs.statement.domain.service.ProgressCalculationService;
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
class ContestResultApplicationServiceTest {

    @Mock
    private ContestResultRepository contestResultRepository;
    @Mock
    private StudentProgressRepository progressRepository;
    @Mock
    private ProgressCalculationService progressCalculationService;

    @InjectMocks
    private ContestResultApplicationService service;

    @Test
    void execute_shouldRecordContestResultAndUpdateProgress() {
        var userId = UUID.randomUUID();
        var contestId = UUID.randomUUID();
        var command = new RecordContestResultCommand(contestId, List.of(userId));
        var progress = StudentProgress.createNew(UserId.from(userId));

        when(contestResultRepository.save(any(ContestResult.class)))
                .thenReturn(Mono.just(ContestResult.record(contestId, UserId.from(userId))));
        when(progressRepository.findByUserId(any(UserId.class)))
                .thenReturn(Mono.just(progress));
        when(progressCalculationService.updateForContest(any()))
                .thenReturn(progress);
        when(progressRepository.save(any(StudentProgress.class)))
                .thenReturn(Mono.just(progress));

        StepVerifier.create(service.execute(command))
                .expectNext(true)
                .verifyComplete();
    }
}
