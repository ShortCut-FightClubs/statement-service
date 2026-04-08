package com.shortcutfightclubs.statement.adapter.outbound.persistence.mapper;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.document.TrainingStatsDocument;
import com.shortcutfightclubs.statement.domain.model.entity.TrainingStats;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class TrainingStatsPersistenceMapper {

    public TrainingStats toDomain(TrainingStatsDocument doc) {
        return new TrainingStats(
                doc.getId(),
                UserId.from(UUID.fromString(doc.getUserId())),
                doc.getTotalLessons(),
                doc.getAttendedLessons(),
                doc.getAttendanceRate(),
                doc.getLastUpdated()
        );
    }

    public TrainingStatsDocument toDocument(TrainingStats domain) {
        var doc = new TrainingStatsDocument();
        doc.setId(domain.getId());
        doc.setUserId(domain.getUserId().value().toString());
        doc.setTotalLessons(domain.getTotalLessons());
        doc.setAttendedLessons(domain.getAttendedLessons());
        doc.setAttendanceRate(domain.getAttendanceRate());
        doc.setLastUpdated(domain.getLastUpdated());
        return doc;
    }
}
