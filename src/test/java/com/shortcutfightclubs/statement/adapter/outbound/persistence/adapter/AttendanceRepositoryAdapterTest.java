package com.shortcutfightclubs.statement.adapter.outbound.persistence.adapter;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.mapper.AttendancePersistenceMapperImpl;
import com.shortcutfightclubs.statement.adapter.outbound.persistence.repository.AttendanceMongoRepository;
import com.shortcutfightclubs.statement.domain.model.entity.Attendance;
import com.shortcutfightclubs.statement.domain.model.vo.LessonId;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttendanceRepositoryAdapterTest {

    @Mock
    private AttendanceMongoRepository mongoRepository;

    private AttendanceRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new AttendanceRepositoryAdapter(mongoRepository, new AttendancePersistenceMapperImpl());
    }

    @Test
    void save_shouldPersistAndReturnAttendance() {
        var attendance = Attendance.record(LessonId.generate(), UserId.generate());
        var document = new AttendancePersistenceMapperImpl().toDocument(attendance);

        when(mongoRepository.save(any())).thenReturn(Mono.just(document));

        StepVerifier.create(adapter.save(attendance))
                .assertNext(saved -> {
                    assertThat(saved.getId()).isEqualTo(attendance.getId());
                    assertThat(saved.getLessonId()).isEqualTo(attendance.getLessonId());
                    assertThat(saved.getUserId()).isEqualTo(attendance.getUserId());
                })
                .verifyComplete();
    }

    @Test
    void findByUserId_shouldReturnAttendancesForUser() {
        var userId = UserId.generate();
        var attendance1 = Attendance.record(LessonId.generate(), userId);
        var attendance2 = Attendance.record(LessonId.generate(), userId);
        var mapper = new AttendancePersistenceMapperImpl();

        when(mongoRepository.findByUserId(userId.value().toString()))
                .thenReturn(Flux.fromIterable(List.of(
                        mapper.toDocument(attendance1),
                        mapper.toDocument(attendance2)
                )));

        StepVerifier.create(adapter.findByUserId(userId))
                .expectNext(attendance1, attendance2)
                .verifyComplete();
    }

    @Test
    void countByUserId_shouldReturnCount() {
        var userId = UserId.generate();

        when(mongoRepository.countByUserId(userId.value().toString()))
                .thenReturn(Mono.just(1L));

        StepVerifier.create(adapter.countByUserId(userId))
                .assertNext(count -> assertThat(count).isEqualTo(1L))
                .verifyComplete();
    }
}
