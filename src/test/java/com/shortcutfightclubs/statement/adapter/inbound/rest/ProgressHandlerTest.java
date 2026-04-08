package com.shortcutfightclubs.statement.adapter.inbound.rest;

import com.shortcutfightclubs.statement.application.dto.response.StudentProgressResponse;
import com.shortcutfightclubs.statement.application.dto.response.TrainingStatsResponse;
import com.shortcutfightclubs.statement.application.port.input.GetStudentProgressUseCase;
import com.shortcutfightclubs.statement.application.port.input.GetTrainingStatsUseCase;
import com.shortcutfightclubs.statement.domain.exception.StudentProgressNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest({ProgressRouter.class, ProgressHandler.class, GlobalExceptionHandler.class})
class ProgressHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private GetStudentProgressUseCase getStudentProgressUseCase;

    @MockitoBean
    private GetTrainingStatsUseCase getTrainingStatsUseCase;

    @Test
    void getProgress_shouldReturnProgress() {
        var studentId = UUID.randomUUID();
        var response = new StudentProgressResponse("id1", studentId, 5, 2, List.of(), Instant.now());

        when(getStudentProgressUseCase.getProgress(any(UUID.class)))
                .thenReturn(Mono.just(response));

        webTestClient.get()
                .uri("/api/v1/progress/{studentId}", studentId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.userId").isEqualTo(studentId.toString())
                .jsonPath("$.lessonsAttended").isEqualTo(5);
    }

    @Test
    void getProgress_shouldReturn404WhenNotFound() {
        var studentId = UUID.randomUUID();

        when(getStudentProgressUseCase.getProgress(any(UUID.class)))
                .thenReturn(Mono.error(new StudentProgressNotFoundException(studentId)));

        webTestClient.get()
                .uri("/api/v1/progress/{studentId}", studentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getStats_shouldReturnStats() {
        var userId = UUID.randomUUID();
        var response = new TrainingStatsResponse("id1", userId, 10, 8, 80.0, Instant.now());

        when(getTrainingStatsUseCase.getStats(any(UUID.class)))
                .thenReturn(Mono.just(response));

        webTestClient.get()
                .uri("/api/v1/stats/{userId}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.attendanceRate").isEqualTo(80.0);
    }
}
