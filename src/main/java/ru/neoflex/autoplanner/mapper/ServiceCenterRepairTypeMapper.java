package ru.neoflex.autoplanner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.autoplanner.dto.ServiceCenterRepairTypeRequestDto;
import ru.neoflex.autoplanner.dto.ServiceCenterRepairTypeResponseDto;
import ru.neoflex.autoplanner.entity.ServiceCenterRepairType;

@Mapper(componentModel = "spring")
public interface ServiceCenterRepairTypeMapper {

    @Mapping(source = "serviceCenter.id", target = "serviceCenterId")
    @Mapping(source = "repairType.id", target = "repairTypeId")
    @Mapping(source = "repairType.name", target = "repairTypeName")
    ServiceCenterRepairTypeResponseDto toDto(ServiceCenterRepairType entity);

    @Mapping(target = "serviceCenter", ignore = true)
    @Mapping(target = "repairType", ignore = true)
    ServiceCenterRepairType toEntity(ServiceCenterRepairTypeRequestDto dto);
}
