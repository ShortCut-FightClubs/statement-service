package com.shortcutfightclubs.statement.domain.exception;

public class AttendanceNotFoundException extends DomainException {

    public AttendanceNotFoundException(String id) {
        super(ErrorCode.ATTENDANCE_NOT_FOUND, "Attendance not found with id: " + id);
    }
}
