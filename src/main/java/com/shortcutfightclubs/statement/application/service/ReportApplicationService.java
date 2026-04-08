package com.shortcutfightclubs.statement.application.service;

import com.shortcutfightclubs.statement.application.dto.response.*;
import com.shortcutfightclubs.statement.application.port.input.*;
import com.shortcutfightclubs.statement.application.port.output.AttendanceRepository;
import com.shortcutfightclubs.statement.application.port.output.ContestResultRepository;
import com.shortcutfightclubs.statement.application.port.output.StudentProgressRepository;
import com.shortcutfightclubs.statement.application.port.output.TrainingStatsRepository;
import com.shortcutfightclubs.statement.domain.exception.StudentProgressNotFoundException;
import com.shortcutfightclubs.statement.domain.model.entity.TrainingStats;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ReportApplicationService implements GetStudentProgressUseCase, GetTrainingStatsUseCase,
        GetStudentReportUseCase, GetGroupReportUseCase, GetTrainerReportUseCase {

    private final StudentProgressRepository progressRepository;
    private final TrainingStatsRepository statsRepository;
    private final AttendanceRepository attendanceRepository;
    private final ContestResultRepository contestResultRepository;

    public ReportApplicationService(StudentProgressRepository progressRepository,
                                    TrainingStatsRepository statsRepository,
                                    AttendanceRepository attendanceRepository,
                                    ContestResultRepository contestResultRepository) {
        this.progressRepository = progressRepository;
        this.statsRepository = statsRepository;
        this.attendanceRepository = attendanceRepository;
        this.contestResultRepository = contestResultRepository;
    }

    @Override
    public Mono<StudentProgressResponse> getProgress(UUID studentId) {
        return progressRepository.findByUserId(UserId.from(studentId))
                .switchIfEmpty(Mono.error(new StudentProgressNotFoundException(studentId)))
                .map(p -> new StudentProgressResponse(
                        p.getId(),
                        p.getUserId().value(),
                        p.getLessonsAttended(),
                        p.getContestsParticipated(),
                        p.getAchievements(),
                        p.getLastUpdated()
                ));
    }

    @Override
    public Mono<TrainingStatsResponse> getStats(UUID userId) {
        return statsRepository.findByUserId(UserId.from(userId))
                .map(s -> new TrainingStatsResponse(
                        s.getId(),
                        s.getUserId().value(),
                        s.getTotalLessons(),
                        s.getAttendedLessons(),
                        s.getAttendanceRate(),
                        s.getLastUpdated()
                ))
                .defaultIfEmpty(new TrainingStatsResponse(null, userId, 0, 0, 0.0, null));
    }

    @Override
    public Mono<StudentReportResponse> getStudentReport(UUID studentId) {
        var userId = UserId.from(studentId);

        var progressMono = progressRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new StudentProgressNotFoundException(studentId)));
        var statsMono = statsRepository.findByUserId(userId)
                .defaultIfEmpty(TrainingStats.createNew(userId));
        var contestResultsFlux = contestResultRepository.findByUserId(userId)
                .map(cr -> new ContestResultResponse(
                        cr.getId(), cr.getContestId(), cr.getUserId().value(),
                        cr.getPlacement(), cr.getScore(), cr.getRecordedAt()
                ))
                .collectList();

        return Mono.zip(progressMono, statsMono, contestResultsFlux)
                .map(tuple -> new StudentReportResponse(
                        studentId,
                        tuple.getT1().getLessonsAttended(),
                        tuple.getT1().getContestsParticipated(),
                        tuple.getT2().getAttendanceRate(),
                        tuple.getT1().getAchievements(),
                        tuple.getT3()
                ));
    }

    @Override
    public Mono<GroupReportResponse> getGroupReport() {
        return progressRepository.findAll()
                .map(p -> new StudentProgressResponse(
                        p.getId(), p.getUserId().value(), p.getLessonsAttended(),
                        p.getContestsParticipated(), p.getAchievements(), p.getLastUpdated()
                ))
                .collectList()
                .map(students -> {
                    double avgRate = students.stream()
                            .mapToInt(StudentProgressResponse::lessonsAttended)
                            .average()
                            .orElse(0.0);
                    return new GroupReportResponse(students.size(), avgRate, students);
                });
    }

    @Override
    public Mono<TrainerReportResponse> getTrainerReport() {
        return Mono.zip(
                progressRepository.countAll(),
                attendanceRepository.countAll(),
                contestResultRepository.countAll()
        ).flatMap(tuple -> {
            int totalStudents = tuple.getT1().intValue();
            int totalLessons = tuple.getT2().intValue();
            int totalContests = tuple.getT3().intValue();

            return statsRepository.findAll()
                    .map(TrainingStats::getAttendanceRate)
                    .collectList()
                    .map(rates -> {
                        double avgRate = rates.stream()
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(0.0);
                        return new TrainerReportResponse(totalStudents, totalLessons, avgRate, totalContests);
                    });
        });
    }
}
