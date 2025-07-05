package ru.neoflex.autoplanner.controller;

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
    public ResponseEntity<?> createUser(@RequestBody UserRequestDto dto){
        try{
            UserResponseDto responseDto = userService.createUser(dto);
            return ResponseEntity.status(201)
                    .body(ApiResponseDto.success("User created successfully", responseDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponseDto.error(e.getMessage()));
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        try{
            UserResponseDto user = userService.getUser(id);
            return ResponseEntity.ok(ApiResponseDto.success(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDto dto){
        try {
            UserResponseDto user = userService.updateUser(id, dto);
            return ResponseEntity.ok(ApiResponseDto.success("User information updated successfully", user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponseDto.success("User deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponseDto.error(e.getMessage()));
        }
    }

}
