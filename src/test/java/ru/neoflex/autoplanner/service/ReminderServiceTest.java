package ru.neoflex.autoplanner.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.entity.Reminder;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.ReminderMapper;
import ru.neoflex.autoplanner.repository.ReminderRepository;
import ru.neoflex.autoplanner.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReminderServiceTest {

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReminderMapper mapper;

    @InjectMocks
    private ReminderService service;

    private User user;
    private Reminder reminder;
    private ReminderResponseDto responseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        reminder = new Reminder();
        reminder.setId(10L);
        reminder.setUser(user);
        reminder.setReminderType("OIL_CHANGE");
        reminder.setRemindDate(LocalDateTime.now());
        reminder.setRepeatIntervalDays(30);
        reminder.setRecurring(true);

        responseDto = new ReminderResponseDto();
    }

    @Test
    void getAllRemindersByUserSuccess() {
        when(reminderRepository.findAllByUserId(1L))
                .thenReturn(List.of(reminder));
        when(mapper.toDto(reminder)).thenReturn(responseDto);

        List<ReminderResponseDto> result =
                service.getAllRemindersByUser(1L);

        assertEquals(1, result.size());
        verify(reminderRepository).findAllByUserId(1L);
    }

    @Test
    void getAllRemindersByUserEmpty() {
        when(reminderRepository.findAllByUserId(1L))
                .thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class,
                () -> service.getAllRemindersByUser(1L));
    }

    @Test
    void createReminderSuccess() {
        ReminderRequestDto dto = new ReminderRequestDto();
        dto.setUserId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));
        when(mapper.toEntity(dto))
                .thenReturn(reminder);
        when(reminderRepository.save(any(Reminder.class)))
                .thenReturn(reminder);
        when(mapper.toDto(reminder))
                .thenReturn(responseDto);

        ReminderResponseDto result =
                service.createReminder(dto);

        assertNotNull(result);
        verify(reminderRepository).save(any(Reminder.class));
    }

    @Test
    void createReminderUserNotFound() {
        ReminderRequestDto dto = new ReminderRequestDto();
        dto.setUserId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.createReminder(dto));
    }

    @Test
    void updateReminderSuccess() {
        ReminderUpdateRequestDto dto =
                new ReminderUpdateRequestDto();

        when(reminderRepository.findById(10L))
                .thenReturn(Optional.of(reminder));
        when(reminderRepository.save(reminder))
                .thenReturn(reminder);
        when(mapper.toDto(reminder))
                .thenReturn(responseDto);

        ReminderResponseDto result =
                service.updateReminder(10L, dto);

        assertNotNull(result);
        verify(mapper).updateEntityFromDto(reminder, dto);
        verify(reminderRepository).save(reminder);
    }

    @Test
    void updateReminderNotFound() {
        when(reminderRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.updateReminder(10L,
                        new ReminderUpdateRequestDto()));
    }

    @Test
    void deleteReminderSuccess() {
        when(reminderRepository.findById(10L))
                .thenReturn(Optional.of(reminder));

        service.deleteReminder(10L);

        verify(reminderRepository).delete(reminder);
    }

    @Test
    void deleteReminderNotFound() {
        when(reminderRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> service.deleteReminder(10L));
    }
}