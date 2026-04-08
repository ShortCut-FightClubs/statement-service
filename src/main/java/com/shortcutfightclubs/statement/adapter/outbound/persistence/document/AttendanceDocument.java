package com.shortcutfightclubs.statement.adapter.outbound.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "attendances")
@Getter
@Setter
public class AttendanceDocument {

    @Id
    private String id;

    @Indexed
    private String lessonId;

    @Indexed
    private String userId;

    private String status;

    private Instant recordedAt;
}
