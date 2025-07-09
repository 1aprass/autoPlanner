package ru.neoflex.autoplanner.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.autoplanner.dto.UserRequestDto;
import ru.neoflex.autoplanner.dto.UserResponseDto;
import ru.neoflex.autoplanner.dto.UserUpdateRequestDto;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.UserMapper;
import ru.neoflex.autoplanner.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class  UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordencoder;
    private final UserMapper usermapper;

    @Transactional
    public UserResponseDto createUser(UserRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalStateException("Email уже зарегистрирован");
        }

        User user = usermapper.dtoToUser(userDto);
        user.setPasswordHash(passwordencoder.encode(userDto.getPasswordHash()));
        User savedUser = userRepository.save(user);

        return usermapper.userToDto(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id){
        User user = getUserOrThrow(id);
        return usermapper.userToDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto dto){
        User user = getUserOrThrow(id);
        usermapper.updateEntityFromDto(user, dto);

        User updatedUser = userRepository.save(user);
        return usermapper.userToDto(updatedUser);
    }
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserOrThrow(id);
        userRepository.delete(user);
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

}
