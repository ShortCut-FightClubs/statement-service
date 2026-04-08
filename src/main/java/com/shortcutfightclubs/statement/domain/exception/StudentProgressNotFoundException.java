package com.shortcutfightclubs.statement.domain.exception;

import java.util.UUID;

public class StudentProgressNotFoundException extends DomainException {

    public StudentProgressNotFoundException(UUID userId) {
        super(ErrorCode.STUDENT_PROGRESS_NOT_FOUND, "Student progress not found for user: " + userId);
    }
}
