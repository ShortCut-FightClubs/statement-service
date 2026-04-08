package com.shortcutfightclubs.statement.adapter.outbound.persistence.mapper;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.document.ContestResultDocument;
import com.shortcutfightclubs.statement.domain.model.entity.ContestResult;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ContestResultPersistenceMapper {

    public ContestResult toDomain(ContestResultDocument doc) {
        return new ContestResult(
                doc.getId(),
                UUID.fromString(doc.getContestId()),
                UserId.from(UUID.fromString(doc.getUserId())),
                doc.getPlacement(),
                doc.getScore(),
                doc.getRecordedAt()
        );
    }

    public ContestResultDocument toDocument(ContestResult domain) {
        var doc = new ContestResultDocument();
        doc.setId(domain.getId());
        doc.setContestId(domain.getContestId().toString());
        doc.setUserId(domain.getUserId().value().toString());
        doc.setPlacement(domain.getPlacement());
        doc.setScore(domain.getScore());
        doc.setRecordedAt(domain.getRecordedAt());
        return doc;
    }
}
