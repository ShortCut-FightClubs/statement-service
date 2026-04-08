package com.shortcutfightclubs.statement.domain.exception;

public class ContestResultNotFoundException extends DomainException {

    public ContestResultNotFoundException(String id) {
        super(ErrorCode.CONTEST_RESULT_NOT_FOUND, "Contest result not found with id: " + id);
    }
}
