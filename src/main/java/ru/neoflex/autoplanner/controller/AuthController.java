package ru.neoflex.autoplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.autoplanner.dto.RegistrationRequestDto;
import ru.neoflex.autoplanner.dto.RegistrationResponseDto;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.UserRegistrationMapper;
import ru.neoflex.autoplanner.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserRegistrationMapper usermapper;


    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto>create(@RequestBody RegistrationRequestDto dto){
        User newUser = userService.userRegistration(dto);
        RegistrationResponseDto responseDto = usermapper.userToDto(newUser);
        return ResponseEntity.ok(responseDto);
    }


}
