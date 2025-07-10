package ru.neoflex.autoplanner.mapper;

import org.mapstruct.*;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.entity.Reminder;
import ru.neoflex.autoplanner.entity.Vehicle;

@Mapper(componentModel = "spring")
public interface ReminderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "type", source = "reminderType")
    @Mapping(target = "vehicleId", source = "vehicle.id")
    ReminderResponseDto toDto(Reminder reminder);

    @Mapping(target = "reminderType", source = "type")
    @Mapping(target = "vehicle", source = "vehicleId", qualifiedByName = "mapVehicleFromId")
    Reminder toEntity(ReminderRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "reminderType", source = "type")
    void updateEntityFromDto(@MappingTarget Reminder reminder, ReminderUpdateRequestDto dto);

    @Named("mapVehicleFromId")
    static Vehicle mapVehicleFromId(Long id) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }


}