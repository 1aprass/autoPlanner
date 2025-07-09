package ru.neoflex.autoplanner.mapper;

import org.mapstruct.*;
import ru.neoflex.autoplanner.dto.DocumentRequestDto;
import ru.neoflex.autoplanner.dto.DocumentResponseDto;
import ru.neoflex.autoplanner.dto.DocumentUpdateRequestDto;
import ru.neoflex.autoplanner.entity.Document;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "createdAt", target = "uploadedAt")
    DocumentResponseDto toDto(Document document);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "vehicle.id", source = "vehicleId")
    @Mapping(target = "createdAt", source = "uploadedAt")
    Document toEntity(DocumentRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget Document document, DocumentUpdateRequestDto dto);
}