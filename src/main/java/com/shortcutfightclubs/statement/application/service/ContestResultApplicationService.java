package com.shortcutfightclubs.statement.application.service;

import com.shortcutfightclubs.statement.application.dto.command.RecordContestResultCommand;
import com.shortcutfightclubs.statement.application.port.input.RecordContestResultUseCase;
import com.shortcutfightclubs.statement.application.port.output.ContestResultRepository;
import com.shortcutfightclubs.statement.application.port.output.StudentProgressRepository;
import com.shortcutfightclubs.statement.domain.model.entity.ContestResult;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import com.shortcutfightclubs.statement.domain.service.ProgressCalculationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ContestResultApplicationService implements RecordContestResultUseCase {

    private final ContestResultRepository contestResultRepository;
    private final StudentProgressRepository progressRepository;
    private final ProgressCalculationService progressCalculationService;

    public ContestResultApplicationService(ContestResultRepository contestResultRepository,
                                           StudentProgressRepository progressRepository,
                                           ProgressCalculationService progressCalculationService) {
        this.contestResultRepository = contestResultRepository;
        this.progressRepository = progressRepository;
        this.progressCalculationService = progressCalculationService;
    }

    @Override
    public Mono<Boolean> execute(RecordContestResultCommand command) {
        return Flux.fromIterable(command.participantIds())
                .flatMap(participantId -> {
                    var userId = UserId.from(participantId);
                    var result = ContestResult.record(command.contestId(), userId);

                    return contestResultRepository.save(result)
                            .then(updateProgress(userId));
                })
                .then(Mono.just(true));
    }

    private Mono<Void> updateProgress(UserId userId) {
        return progressRepository.findByUserId(userId)
                .defaultIfEmpty(progressCalculationService.initializeProgress(userId))
                .map(progressCalculationService::updateForContest)
                .flatMap(progressRepository::save)
                .then();
    }
}
