package ru.neoflex.autoplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.neoflex.autoplanner.dto.UserRequestDto;
import ru.neoflex.autoplanner.dto.UserResponseDto;
import ru.neoflex.autoplanner.dto.UserUpdateRequestDto;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.UserMapper;
import ru.neoflex.autoplanner.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserResponseDto responseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPasswordHash("password");

        responseDto = new UserResponseDto();
    }

    @Test
    void createUserSuccess() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("test@example.com");
        dto.setPasswordHash("password");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userMapper.dtoToUser(dto)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.createUser(dto);

        assertNotNull(result);
        verify(userRepository).save(user);
        assertEquals(user.getPasswordHash(), "encodedPassword");
    }

    @Test
    void createUserEmailAlreadyExists() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("test@example.com");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> userService.createUser(dto));
    }

    @Test
    void getUserSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.userToDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.getUser(1L);

        assertNotNull(result);
        verify(userRepository).findById(1L);
        verify(userMapper).userToDto(user);
    }

    @Test
    void getUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.getUser(1L));
    }

    @Test
    void updateUserSuccess() {
        UserUpdateRequestDto dto = new UserUpdateRequestDto();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userMapper).updateEntityFromDto(user, dto);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.userToDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.updateUser(1L, dto);

        assertNotNull(result);
        verify(userRepository).save(user);
        verify(userMapper).updateEntityFromDto(user, dto);
    }

    @Test
    void updateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.updateUser(1L, new UserUpdateRequestDto()));
    }

    @Test
    void deleteUserSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void deleteUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.deleteUser(1L));
    }
}