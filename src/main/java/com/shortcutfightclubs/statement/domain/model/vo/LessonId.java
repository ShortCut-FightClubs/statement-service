package com.shortcutfightclubs.statement.domain.model.vo;

import java.util.Objects;
import java.util.UUID;

public record LessonId(UUID value) {

    public LessonId {
        Objects.requireNonNull(value, "LessonId value must not be null");
    }

    public static LessonId from(UUID value) {
        return new LessonId(value);
    }

    public static LessonId from(String value) {
        return new LessonId(UUID.fromString(value));
    }

    public static LessonId generate() {
        return new LessonId(UUID.randomUUID());
    }
}
