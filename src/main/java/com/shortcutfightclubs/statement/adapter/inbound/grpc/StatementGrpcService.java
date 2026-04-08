package com.shortcutfightclubs.statement.adapter.inbound.grpc;

import com.shortcutfightclubs.statement.application.dto.command.RecordAttendanceCommand;
import com.shortcutfightclubs.statement.application.dto.command.RecordContestResultCommand;
import com.shortcutfightclubs.statement.application.port.input.GetStudentProgressUseCase;
import com.shortcutfightclubs.statement.application.port.input.RecordAttendanceUseCase;
import com.shortcutfightclubs.statement.application.port.input.RecordContestResultUseCase;
import com.shortcutfightclubs.statement.grpc.StatementServiceGrpc;
import com.shortcutfightclubs.statement.grpc.StatementServiceProto.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class StatementGrpcService extends StatementServiceGrpc.StatementServiceImplBase {

    private final RecordAttendanceUseCase recordAttendanceUseCase;
    private final RecordContestResultUseCase recordContestResultUseCase;
    private final GetStudentProgressUseCase getStudentProgressUseCase;

    public StatementGrpcService(RecordAttendanceUseCase recordAttendanceUseCase,
                                RecordContestResultUseCase recordContestResultUseCase,
                                GetStudentProgressUseCase getStudentProgressUseCase) {
        this.recordAttendanceUseCase = recordAttendanceUseCase;
        this.recordContestResultUseCase = recordContestResultUseCase;
        this.getStudentProgressUseCase = getStudentProgressUseCase;
    }

    @Override
    public void recordAttendance(RecordAttendanceRequest request,
                                 StreamObserver<RecordAttendanceResponse> responseObserver) {
        var command = new RecordAttendanceCommand(
                UUID.fromString(request.getLessonId()),
                request.getParticipantIdsList().stream().map(UUID::fromString).toList()
        );

        recordAttendanceUseCase.execute(command)
                .subscribe(
                        success -> {
                            responseObserver.onNext(RecordAttendanceResponse.newBuilder()
                                    .setSuccess(success)
                                    .build());
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("Error recording attendance", error);
                            responseObserver.onError(error);
                        }
                );
    }

    @Override
    public void recordContestResult(RecordContestResultRequest request,
                                    StreamObserver<RecordContestResultResponse> responseObserver) {
        var command = new RecordContestResultCommand(
                UUID.fromString(request.getContestId()),
                request.getParticipantIdsList().stream().map(UUID::fromString).toList()
        );

        recordContestResultUseCase.execute(command)
                .subscribe(
                        success -> {
                            responseObserver.onNext(RecordContestResultResponse.newBuilder()
                                    .setSuccess(success)
                                    .build());
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("Error recording contest result", error);
                            responseObserver.onError(error);
                        }
                );
    }

    @Override
    public void getStudentProgress(GetStudentProgressRequest request,
                                   StreamObserver<GetStudentProgressResponse> responseObserver) {
        var studentId = UUID.fromString(request.getStudentId());

        getStudentProgressUseCase.getProgress(studentId)
                .subscribe(
                        progress -> {
                            responseObserver.onNext(GetStudentProgressResponse.newBuilder()
                                    .setStudentId(progress.userId().toString())
                                    .setLessonsAttended(progress.lessonsAttended())
                                    .setContestsParticipated(progress.contestsParticipated())
                                    .build());
                            responseObserver.onCompleted();
                        },
                        error -> {
                            log.error("Error getting student progress", error);
                            responseObserver.onError(error);
                        }
                );
    }
}
