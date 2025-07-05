package ru.neoflex.autoplanner.service;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.neoflex.autoplanner.dto.UserRequestDto;
import ru.neoflex.autoplanner.dto.UserResponseDto;
import ru.neoflex.autoplanner.dto.UserUpdateRequestDto;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.UserMapper;
import ru.neoflex.autoplanner.repository.UserRepository;

@Data
@Getter
@Setter
@AllArgsConstructor
@Service
public class  UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordencoder;
    private final UserMapper usermapper;

    public UserResponseDto createUser(UserRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email уже зарегистрирован");
        }

        User user = usermapper.dtoToUser(userDto);
        user.setPasswordHash(passwordencoder.encode(userDto.getPasswordHash()));
        User savedUser = userRepository.save(user);

        return usermapper.userToDto(savedUser);
    }

    public UserResponseDto getUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return usermapper.userToDto(user);
    }

    public UserResponseDto updateUser(Long id, UserUpdateRequestDto dto){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        usermapper.updateEntityFromDto(user, dto);

        User updatedUser = userRepository.save(user);
        return usermapper.userToDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userRepository.delete(user);
    }

}
