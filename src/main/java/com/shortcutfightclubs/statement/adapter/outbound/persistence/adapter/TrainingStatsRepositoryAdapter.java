package com.shortcutfightclubs.statement.adapter.outbound.persistence.adapter;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.mapper.TrainingStatsPersistenceMapper;
import com.shortcutfightclubs.statement.adapter.outbound.persistence.repository.TrainingStatsMongoRepository;
import com.shortcutfightclubs.statement.application.port.output.TrainingStatsRepository;
import com.shortcutfightclubs.statement.domain.model.entity.TrainingStats;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TrainingStatsRepositoryAdapter implements TrainingStatsRepository {

    private final TrainingStatsMongoRepository mongoRepository;
    private final TrainingStatsPersistenceMapper mapper;

    public TrainingStatsRepositoryAdapter(TrainingStatsMongoRepository mongoRepository,
                                           TrainingStatsPersistenceMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<TrainingStats> save(TrainingStats stats) {
        return mongoRepository.save(mapper.toDocument(stats))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<TrainingStats> findByUserId(UserId userId) {
        return mongoRepository.findByUserId(userId.value().toString())
                .map(mapper::toDomain);
    }

    @Override
    public Flux<TrainingStats> findAll() {
        return mongoRepository.findAll()
                .map(mapper::toDomain);
    }
}
