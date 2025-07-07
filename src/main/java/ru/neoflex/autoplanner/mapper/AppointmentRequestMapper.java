package ru.neoflex.autoplanner.mapper;

import org.mapstruct.*;
import ru.neoflex.autoplanner.dto.AppointmentRequestResponseDto;
import ru.neoflex.autoplanner.entity.AppointmentRequest;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.entity.Vehicle;
import ru.neoflex.autoplanner.entity.ServiceCenter;

@Mapper(componentModel = "spring")
public interface AppointmentRequestMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "serviceCenter.id", target = "serviceCenterId")
    @Mapping(source = "serviceDate", target = "requestedDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "comments", target = "comment")
    AppointmentRequestResponseDto toDto(AppointmentRequest entity);
}