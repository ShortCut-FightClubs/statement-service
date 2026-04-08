package com.shortcutfightclubs.statement.adapter.inbound.grpc;

import com.shortcutfightclubs.statement.application.dto.response.StudentProgressResponse;
import com.shortcutfightclubs.statement.application.port.input.GetStudentProgressUseCase;
import com.shortcutfightclubs.statement.application.port.input.RecordAttendanceUseCase;
import com.shortcutfightclubs.statement.application.port.input.RecordContestResultUseCase;
import com.shortcutfightclubs.statement.grpc.StatementServiceGrpc;
import com.shortcutfightclubs.statement.grpc.StatementServiceProto.*;
import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatementGrpcServiceTest {

    @Mock
    private RecordAttendanceUseCase recordAttendanceUseCase;
    @Mock
    private RecordContestResultUseCase recordContestResultUseCase;
    @Mock
    private GetStudentProgressUseCase getStudentProgressUseCase;

    private io.grpc.Server server;
    private ManagedChannel channel;
    private StatementServiceGrpc.StatementServiceBlockingStub blockingStub;

    @BeforeEach
    void setUp() throws Exception {
        var serverName = InProcessServerBuilder.generateName();
        var grpcService = new StatementGrpcService(
                recordAttendanceUseCase, recordContestResultUseCase, getStudentProgressUseCase);

        server = InProcessServerBuilder.forName(serverName)
                .directExecutor()
                .addService(grpcService)
                .build()
                .start();

        channel = InProcessChannelBuilder.forName(serverName)
                .directExecutor()
                .build();

        blockingStub = StatementServiceGrpc.newBlockingStub(channel);
    }

    @AfterEach
    void tearDown() {
        channel.shutdownNow();
        server.shutdownNow();
    }

    @Test
    void recordAttendance_shouldReturnSuccess() {
        when(recordAttendanceUseCase.execute(any()))
                .thenReturn(Mono.just(true));

        var response = blockingStub.recordAttendance(RecordAttendanceRequest.newBuilder()
                .setLessonId(UUID.randomUUID().toString())
                .addParticipantIds(UUID.randomUUID().toString())
                .build());

        assertThat(response.getSuccess()).isTrue();
    }

    @Test
    void getStudentProgress_shouldReturnProgress() {
        var studentId = UUID.randomUUID();
        when(getStudentProgressUseCase.getProgress(any()))
                .thenReturn(Mono.just(new StudentProgressResponse(
                        "id1", studentId, 5, 2, List.of(), Instant.now()
                )));

        var response = blockingStub.getStudentProgress(GetStudentProgressRequest.newBuilder()
                .setStudentId(studentId.toString())
                .build());

        assertThat(response.getStudentId()).isEqualTo(studentId.toString());
        assertThat(response.getLessonsAttended()).isEqualTo(5);
        assertThat(response.getContestsParticipated()).isEqualTo(2);
    }
}
