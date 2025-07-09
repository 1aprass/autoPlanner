package ru.neoflex.autoplanner.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.dto.UserRequestDto;
import ru.neoflex.autoplanner.dto.UserResponseDto;
import ru.neoflex.autoplanner.dto.UserUpdateRequestDto;
import ru.neoflex.autoplanner.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    public ResponseEntity<ApiResponseDto<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto dto){

        UserResponseDto responseDto = userService.createUser(dto);
        return ResponseEntity.status(201)
                .body(ApiResponseDto.success("User created successfully", responseDto));


    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> getUser(@Valid @PathVariable Long id){

        UserResponseDto user = userService.getUser(id);
        return ResponseEntity.ok(ApiResponseDto.success(user));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> updateUser(
            @Valid @PathVariable Long id, @RequestBody UserUpdateRequestDto dto){

        UserResponseDto user = userService.updateUser(id, dto);
        return ResponseEntity.ok(ApiResponseDto.success("User information updated successfully", user));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<String>> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponseDto.success("User deleted successfully"));

    }

}
