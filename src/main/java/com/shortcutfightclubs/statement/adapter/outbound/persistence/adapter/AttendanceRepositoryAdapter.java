package com.shortcutfightclubs.statement.adapter.outbound.persistence.adapter;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.mapper.AttendancePersistenceMapper;
import com.shortcutfightclubs.statement.adapter.outbound.persistence.repository.AttendanceMongoRepository;
import com.shortcutfightclubs.statement.application.port.output.AttendanceRepository;
import com.shortcutfightclubs.statement.domain.model.entity.Attendance;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AttendanceRepositoryAdapter implements AttendanceRepository {

    private final AttendanceMongoRepository mongoRepository;
    private final AttendancePersistenceMapper mapper;

    public AttendanceRepositoryAdapter(AttendanceMongoRepository mongoRepository,
                                       AttendancePersistenceMapper mapper) {
        this.mongoRepository = mongoRepository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Attendance> save(Attendance attendance) {
        return mongoRepository.save(mapper.toDocument(attendance))
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Attendance> findByUserId(UserId userId) {
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
