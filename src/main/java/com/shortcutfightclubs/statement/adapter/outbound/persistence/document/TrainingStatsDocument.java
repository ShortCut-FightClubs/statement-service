package com.shortcutfightclubs.statement.adapter.outbound.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "training_stats")
@Getter
@Setter
public class TrainingStatsDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private int totalLessons;

    private int attendedLessons;

    private double attendanceRate;

    private Instant lastUpdated;
}
