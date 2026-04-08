package com.shortcutfightclubs.statement.adapter.outbound.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "student_progress")
@Getter
@Setter
public class StudentProgressDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private int lessonsAttended;

    private int contestsParticipated;

    private List<String> achievements;

    private Instant lastUpdated;
}
