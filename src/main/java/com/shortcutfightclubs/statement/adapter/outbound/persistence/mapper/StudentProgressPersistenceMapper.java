package com.shortcutfightclubs.statement.adapter.outbound.persistence.mapper;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.document.StudentProgressDocument;
import com.shortcutfightclubs.statement.domain.model.entity.StudentProgress;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class StudentProgressPersistenceMapper {

    public StudentProgress toDomain(StudentProgressDocument doc) {
        return new StudentProgress(
                doc.getId(),
                UserId.from(UUID.fromString(doc.getUserId())),
                doc.getLessonsAttended(),
                doc.getContestsParticipated(),
                doc.getAchievements(),
                doc.getLastUpdated()
        );
    }

    public StudentProgressDocument toDocument(StudentProgress domain) {
        var doc = new StudentProgressDocument();
        doc.setId(domain.getId());
        doc.setUserId(domain.getUserId().value().toString());
        doc.setLessonsAttended(domain.getLessonsAttended());
        doc.setContestsParticipated(domain.getContestsParticipated());
        doc.setAchievements(domain.getAchievements());
        doc.setLastUpdated(domain.getLastUpdated());
        return doc;
    }
}
