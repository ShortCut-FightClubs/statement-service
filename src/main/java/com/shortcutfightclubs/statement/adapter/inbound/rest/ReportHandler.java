package com.shortcutfightclubs.statement.adapter.inbound.rest;

import com.shortcutfightclubs.statement.application.port.input.GetGroupReportUseCase;
import com.shortcutfightclubs.statement.application.port.input.GetStudentReportUseCase;
import com.shortcutfightclubs.statement.application.port.input.GetTrainerReportUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class ReportHandler {

    private final GetStudentReportUseCase getStudentReportUseCase;
    private final GetGroupReportUseCase getGroupReportUseCase;
    private final GetTrainerReportUseCase getTrainerReportUseCase;

    public ReportHandler(GetStudentReportUseCase getStudentReportUseCase,
                         GetGroupReportUseCase getGroupReportUseCase,
                         GetTrainerReportUseCase getTrainerReportUseCase) {
        this.getStudentReportUseCase = getStudentReportUseCase;
        this.getGroupReportUseCase = getGroupReportUseCase;
        this.getTrainerReportUseCase = getTrainerReportUseCase;
    }

    public Mono<ServerResponse> getStudentReport(ServerRequest request) {
        var studentId = UUID.fromString(request.pathVariable("studentId"));
        return getStudentReportUseCase.getStudentReport(studentId)
                .flatMap(report -> ServerResponse.ok().bodyValue(report));
    }

    public Mono<ServerResponse> getGroupReport(ServerRequest request) {
        return getGroupReportUseCase.getGroupReport()
                .flatMap(report -> ServerResponse.ok().bodyValue(report));
    }

    public Mono<ServerResponse> getTrainerReport(ServerRequest request) {
        return getTrainerReportUseCase.getTrainerReport()
                .flatMap(report -> ServerResponse.ok().bodyValue(report));
    }
}
