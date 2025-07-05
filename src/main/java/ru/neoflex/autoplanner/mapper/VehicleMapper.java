package ru.neoflex.autoplanner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.autoplanner.dto.VehicleCreateRequestDto;
import ru.neoflex.autoplanner.dto.VehicleResponseDto;
import ru.neoflex.autoplanner.dto.VehicleUpdateRequestDto;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.entity.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {


    @Mapping(target = "user", ignore = true)
    Vehicle toEntity(VehicleCreateRequestDto dto);

    @Mapping(source = "user.id", target = "userId")
    VehicleResponseDto toResponse(Vehicle entity);

    default void updateEntityFromDto(Vehicle entity, VehicleUpdateRequestDto dto) {
        if (dto == null || entity == null) return;

        entity.setMake(dto.getMake());
        entity.setModel(dto.getModel());
        entity.setYear(dto.getYear());
        entity.setLicensePlate(dto.getLicensePlate());
        entity.setCurrentOdometer(dto.getCurrentOdometer());
    }
}

