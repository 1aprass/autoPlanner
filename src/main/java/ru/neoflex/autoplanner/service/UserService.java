package ru.neoflex.autoplanner.service;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.neoflex.autoplanner.dto.RegistrationRequestDto;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.UserRegistrationMapper;
import ru.neoflex.autoplanner.repository.UserRepository;

@Data
@Getter
@Setter
@AllArgsConstructor
@Service
public class  UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordencoder;
    private final UserRegistrationMapper usermapper;

    public User userRegistration(RegistrationRequestDto userDto){
        if (userRepository.existsByEmail(userDto.getEmail())){
            throw new RuntimeException("Email уже зарегистрирован");
        }
        else{
            User user = usermapper.dtoToUser(userDto);
            user.setPasswordHash(passwordencoder.encode(userDto.getPasswordHash()));
            return userRepository.save(user);
        }

    }

}
