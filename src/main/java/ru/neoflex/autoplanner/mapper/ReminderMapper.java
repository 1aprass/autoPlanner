package ru.neoflex.autoplanner.mapper;

import org.mapstruct.*;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.entity.Reminder;

@Mapper(componentModel = "spring")
public interface ReminderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "type", source = "reminderType")
    @Mapping(target = "notified", source = "sent")
    ReminderResponseDto toDto(Reminder reminder);

    @Mapping(target = "reminderType", source = "type")
    @Mapping(target = "sent", source = "notified")
    Reminder toEntity(ReminderRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "reminderType", source = "type")
    @Mapping(target = "sent", source = "notified")
    void updateEntityFromDto(@MappingTarget Reminder reminder, ReminderUpdateRequestDto dto);
}