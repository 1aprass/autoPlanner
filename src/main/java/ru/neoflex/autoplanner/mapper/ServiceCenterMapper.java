package ru.neoflex.autoplanner.mapper;

import org.mapstruct.*;
import ru.neoflex.autoplanner.dto.ServiceCenterRequestDto;
import ru.neoflex.autoplanner.dto.ServiceCenterResponseDto;
import ru.neoflex.autoplanner.dto.ServiceCenterUpdateRequestDto;
import ru.neoflex.autoplanner.entity.ServiceCenter;

@Mapper(componentModel = "spring")
public interface ServiceCenterMapper {

    ServiceCenterResponseDto toDto(ServiceCenter entity);

    ServiceCenter toEntity(ServiceCenterRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ServiceCenterUpdateRequestDto dto, @MappingTarget ServiceCenter entity);
}
