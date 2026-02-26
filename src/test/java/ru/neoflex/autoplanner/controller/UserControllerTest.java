package ru.neoflex.autoplanner.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.UserService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController controller;



    @Test
    void createUserSuccess() {
        UserRequestDto requestDto = new UserRequestDto(
                "test@example.com", "hashed123", "John",
                "Doe", "1234567890", "USER"
        );
        UserResponseDto responseDto = new UserResponseDto(
                1L, "John", "Doe", "1234567890",
                LocalDateTime.now(), LocalDateTime.now(), "test@example.com", "USER"
        );

        when(userService.createUser(requestDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<UserResponseDto>> response = controller.createUser(requestDto);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("test@example.com", response.getBody().getData().getEmail());
        verify(userService).createUser(requestDto);
    }

    @Test
    void createUserDuplicateEmail() {
        UserRequestDto requestDto = new UserRequestDto(
                "test@example.com", "hashed123", "John", "Doe", "1234567890", "USER"
        );

        when(userService.createUser(requestDto))
                .thenThrow(new IllegalArgumentException("A user with this email already exists"));
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> controller.createUser(requestDto));

        assertEquals("A user with this email already exists", exception.getMessage());
        verify(userService).createUser(requestDto);
    }

    @Test
    void getUserSuccess() {
        Long userId = 1L;
        UserResponseDto responseDto = new UserResponseDto(
                userId, "John", "Doe", "1234567890",
                LocalDateTime.now(), LocalDateTime.now(), "test@example.com", "USER"
        );

        when(userService.getUser(userId)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<UserResponseDto>> response = controller.getUser(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userId, response.getBody().getData().getId());
        verify(userService).getUser(userId);
    }

    @Test
    void getUserNotFound() {
        Long userId = 99L;

        when(userService.getUser(userId)).thenThrow(new NoSuchElementException("User not found"));

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> controller.getUser(userId));
        assertEquals("User not found", exception.getMessage());
        verify(userService).getUser(userId);
    }

    @Test
    void updateUserSuccess() {
        Long userId = 1L;
        UserUpdateRequestDto updateDto = new UserUpdateRequestDto(
                "John", "Doe", "john@example.com", "1234567890", "ADMIN"
        );
        UserResponseDto responseDto = new UserResponseDto(
                userId, "John", "Doe", "1234567890",
                LocalDateTime.now(), LocalDateTime.now(), "john@example.com", "ADMIN"
        );

        when(userService.updateUser(userId, updateDto)).thenReturn(responseDto);
        ResponseEntity<ApiResponseDto<UserResponseDto>> response = controller.updateUser(userId, updateDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("john@example.com", response.getBody().getData().getEmail());
        verify(userService).updateUser(userId, updateDto);
    }

    @Test
    void updateUserNotFound() {
        Long userId = 99L;
        UserUpdateRequestDto updateDto = new UserUpdateRequestDto(
                "John", "Doe", "john@example.com", "1234567890", "ADMIN"
        );

        when(userService.updateUser(userId, updateDto)).thenThrow(new NoSuchElementException("User not found"));
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> controller.updateUser(userId, updateDto));

        assertEquals("User not found", exception.getMessage());
        verify(userService).updateUser(userId, updateDto);
    }

    @Test
    void deleteUserSuccess() {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);
        ResponseEntity<ApiResponseDto<String>> response = controller.deleteUser(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("User deleted successfully", response.getBody().getMessage());
        verify(userService).deleteUser(userId);
    }

    @Test
    void deleteUserNotFound() {
        Long userId = 99L;

        doThrow(new NoSuchElementException("User not found")).when(userService).deleteUser(userId);
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> controller.deleteUser(userId));

        assertEquals("User not found", exception.getMessage());
        verify(userService).deleteUser(userId);
    }

    @Test
    void deleteUserInternalServerError() {
        Long userId = 1L;

        doThrow(new RuntimeException("Internal server error")).when(userService).deleteUser(userId);
        Exception exception = assertThrows(RuntimeException.class,
                () -> controller.deleteUser(userId));

        assertEquals("Internal server error", exception.getMessage());
        verify(userService).deleteUser(userId);
    }
}