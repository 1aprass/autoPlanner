package ru.neoflex.autoplanner.mapper;

import org.mapstruct.*;
import ru.neoflex.autoplanner.dto.UserRequestDto;
import ru.neoflex.autoplanner.dto.UserResponseDto;
import ru.neoflex.autoplanner.dto.UserUpdateRequestDto;
import ru.neoflex.autoplanner.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "passwordHash", ignore = true)
    User dtoToUser(UserRequestDto dto);
    UserResponseDto userToDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget User user, UserUpdateRequestDto dto);
}
