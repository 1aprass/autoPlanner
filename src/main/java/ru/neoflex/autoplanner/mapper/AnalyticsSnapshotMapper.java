package ru.neoflex.autoplanner.mapper;

import org.mapstruct.*;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotResponseDto;
import ru.neoflex.autoplanner.entity.AnalyticsSnapshot;

@Mapper(componentModel = "spring")
public interface AnalyticsSnapshotMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "mostCommonRepairType.id", target = "mostCommonRepairTypeId")
    AnalyticsSnapshotResponseDto toDto(AnalyticsSnapshot entity);
}
