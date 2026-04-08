package com.shortcutfightclubs.statement.adapter.outbound.persistence.adapter;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.mapper.StudentProgressPersistenceMapper;
import com.shortcutfightclubs.statement.adapter.outbound.persistence.repository.StudentProgressMongoRepository;
import com.shortcutfightclubs.statement.application.port.output.StudentProgressRepository;
import com.shortcutfightclubs.statement.domain.model.entity.StudentProgress;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class StudentProgressRepositoryAdapter implements StudentProgressRepository {

    private final StudentProgressMongoRepository mongoRepository;
    private final StudentProgressPersistenceMapper mapper;

    public StudentProgressRepositoryAdapter(StudentProgressMongoRepository mongoRepository,
                                             StudentProgressPersistenceMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<StudentProgress> save(StudentProgress progress) {
        return mongoRepository.save(mapper.toDocument(progress))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<StudentProgress> findByUserId(UserId userId) {
        return mongoRepository.findByUserId(userId.value().toString())
                .map(mapper::toDomain);
    }

    @Override
    public Flux<StudentProgress> findAll() {
        return mongoRepository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Long> countAll() {
        return mongoRepository.count();
    }
}
