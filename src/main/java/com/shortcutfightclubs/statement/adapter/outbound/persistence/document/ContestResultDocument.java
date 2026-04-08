package com.shortcutfightclubs.statement.adapter.outbound.persistence.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "contest_results")
@Getter
@Setter
public class ContestResultDocument {

    @Id
    private String id;

    @Indexed
    private String contestId;

    @Indexed
    private String userId;

    private int placement;

    private int score;

    private Instant recordedAt;
}
