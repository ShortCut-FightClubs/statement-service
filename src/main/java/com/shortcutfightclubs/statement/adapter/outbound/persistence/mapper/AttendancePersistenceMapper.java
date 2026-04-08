package com.shortcutfightclubs.statement.adapter.outbound.persistence.mapper;

import com.shortcutfightclubs.statement.adapter.outbound.persistence.document.AttendanceDocument;
import com.shortcutfightclubs.statement.domain.model.entity.Attendance;
import com.shortcutfightclubs.statement.domain.model.enums.AttendanceStatus;
import com.shortcutfightclubs.statement.domain.model.vo.LessonId;
import com.shortcutfightclubs.statement.domain.model.vo.UserId;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class AttendancePersistenceMapper {

    public Attendance toDomain(AttendanceDocument doc) {
        return new Attendance(
                doc.getId(),
                LessonId.from(UUID.fromString(doc.getLessonId())),
                UserId.from(UUID.fromString(doc.getUserId())),
                AttendanceStatus.valueOf(doc.getStatus()),
                doc.getRecordedAt()
        );
    }

    public AttendanceDocument toDocument(Attendance domain) {
        var doc = new AttendanceDocument();
        doc.setId(domain.getId());
        doc.setLessonId(domain.getLessonId().value().toString());
        doc.setUserId(domain.getUserId().value().toString());
        doc.setStatus(domain.getStatus().name());
        doc.setRecordedAt(domain.getRecordedAt());
        return doc;
    }
}
