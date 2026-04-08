package com.shortcutfightclubs.statement.adapter.outbound.persistence.adapter;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.mapper.ContestResultPersistenceMapper;
import com.shortcutfightclubs.statement.adapter.outbound.persistence.repository.ContestResultMongoRepository;
import com.shortcutfightclubs.statement.application.port.output.ContestResultRepository;
import com.shortcutfightclubs.statement.domain.model.entity.ContestResult;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ContestResultRepositoryAdapter implements ContestResultRepository {

    private final ContestResultMongoRepository mongoRepository;
    private final ContestResultPersistenceMapper mapper;

    public ContestResultRepositoryAdapter(ContestResultMongoRepository mongoRepository,
                                           ContestResultPersistenceMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<ContestResult> save(ContestResult result) {
        return mongoRepository.save(mapper.toDocument(result))
                .map(mapper::toDomain);
    }

    @Override
    public Flux<ContestResult> findByUserId(UserId userId) {
        return mongoRepository.findByUserId(userId.value().toString())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Long> countByUserId(UserId userId) {
        return mongoRepository.countByUserId(userId.value().toString());
    }

    @Override
    public Mono<Long> countAll() {
        return mongoRepository.count();
    }
}
