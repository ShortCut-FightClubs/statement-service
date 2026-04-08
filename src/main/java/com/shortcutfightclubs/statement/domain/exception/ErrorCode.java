package com.shortcutfightclubs.statement.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    ATTENDANCE_NOT_FOUND("1001", "Attendance record not found", HttpStatus.NOT_FOUND),
    STUDENT_PROGRESS_NOT_FOUND("2001", "Student progress not found", HttpStatus.NOT_FOUND),
    CONTEST_RESULT_NOT_FOUND("3001", "Contest result not found", HttpStatus.NOT_FOUND),
    TRAINING_STATS_NOT_FOUND("4001", "Training stats not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("9001", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getFullCode() {
        return "STM-" + code;
    }
}
