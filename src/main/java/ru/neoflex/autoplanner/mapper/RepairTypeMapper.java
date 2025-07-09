package ru.neoflex.autoplanner.mapper;

import org.mapstruct.*;
import ru.neoflex.autoplanner.dto.RepairTypeResponseDto;
import ru.neoflex.autoplanner.entity.RepairType;

@Mapper(componentModel = "spring")
public interface RepairTypeMapper {
    RepairTypeResponseDto toDto(RepairType entity);
}