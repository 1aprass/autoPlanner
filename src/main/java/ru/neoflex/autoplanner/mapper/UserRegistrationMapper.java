package ru.neoflex.autoplanner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.neoflex.autoplanner.dto.RegistrationRequestDto;
import ru.neoflex.autoplanner.dto.RegistrationResponseDto;
import ru.neoflex.autoplanner.entity.User;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {
    @Mapping(target = "passwordHash", ignore = true)
    User dtoToUser(RegistrationRequestDto dto);

    RegistrationResponseDto userToDto(User user);
}
