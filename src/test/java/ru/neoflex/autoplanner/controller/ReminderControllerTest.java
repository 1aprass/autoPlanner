package ru.neoflex.autoplanner.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.neoflex.autoplanner.dto.ApiResponseDto;
import ru.neoflex.autoplanner.dto.ReminderRequestDto;
import ru.neoflex.autoplanner.dto.ReminderResponseDto;
import ru.neoflex.autoplanner.dto.ReminderUpdateRequestDto;
import ru.neoflex.autoplanner.service.ReminderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReminderControllerTest {

    @Mock
    private ReminderService reminderService;

    @InjectMocks
    private ReminderController controller;

    @Test
    void getRemindersSuccessful(){

        Long userId = 1L;
        List<ReminderResponseDto> reminders = List.of(
                new ReminderResponseDto(1L, userId, 2L, "OIL_CHANGE",
                        LocalDateTime.now(), false, "Check oil", 30, true)
        );

        when(reminderService.getAllRemindersByUser(userId)).thenReturn(reminders);

        ResponseEntity<ApiResponseDto<List<ReminderResponseDto>>> response = controller.getReminders(userId);

        verify(reminderService).getAllRemindersByUser(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertEquals(1, response.getBody().getData().size());

    }
    @Test
    void getRemindersNotFound() {
        Long userId = 99L;

        when(reminderService.getAllRemindersByUser(userId))
                .thenThrow(new NoSuchElementException("No reminders found"));

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> controller.getReminders(userId));

        assertEquals("No reminders found", exception.getMessage());
        verify(reminderService).getAllRemindersByUser(userId);
    }

    @Test
    void createReminderSuccess() {
        ReminderRequestDto requestDto = new ReminderRequestDto();
        requestDto.setUserId(1L);
        requestDto.setVehicleId(2L);
        requestDto.setType("OIL_CHANGE");
        requestDto.setRemindDate(LocalDateTime.now());
        requestDto.setSent(false);
        requestDto.setNotes("Check oil");
        requestDto.setRepeatIntervalDays(30);
        requestDto.setRecurring(true);

        ReminderResponseDto responseDto = new ReminderResponseDto(1L, 1L, 2L, "OIL_CHANGE",
                LocalDateTime.now(), false, "Check oil", 30, true);

        when(reminderService.createReminder(requestDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<ReminderResponseDto>> response = controller.createReminder(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(responseDto.getId(), response.getBody().getData().getId());
        verify(reminderService).createReminder(requestDto);
    }

    @Test
    void createReminderInvalidRequest() {
        ReminderRequestDto invalidDto = new ReminderRequestDto();

        when(reminderService.createReminder(invalidDto))
                .thenThrow(new IllegalArgumentException("userId is required"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> controller.createReminder(invalidDto));

        assertEquals("userId is required", exception.getMessage());
        verify(reminderService).createReminder(invalidDto);
    }

    @Test
    void updateReminderSuccess() {
        Long reminderId = 1L;
        ReminderUpdateRequestDto updateDto = new ReminderUpdateRequestDto();
        updateDto.setType("TIRE_CHECK");
        updateDto.setRemindDate(LocalDateTime.now());
        updateDto.setSent(true);
        updateDto.setNotes("Check tires");
        updateDto.setRepeatIntervalDays(60);
        updateDto.setRecurring(false);

        ReminderResponseDto responseDto = new ReminderResponseDto(reminderId, 1L, 2L, "TIRE_CHECK",
                LocalDateTime.now(), true, "Check tires", 60, false);

        when(reminderService.updateReminder(reminderId, updateDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<ReminderResponseDto>> response =
                controller.updateReminder(reminderId, updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(reminderId, response.getBody().getData().getId());
        verify(reminderService).updateReminder(reminderId, updateDto);
    }

    @Test
    void updateReminderNotFound() {
        Long reminderId = 99L;
        ReminderUpdateRequestDto updateDto = new ReminderUpdateRequestDto();

        when(reminderService.updateReminder(reminderId, updateDto))
                .thenThrow(new NoSuchElementException("Reminder not found"));

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> controller.updateReminder(reminderId, updateDto));

        assertEquals("Reminder not found", exception.getMessage());
        verify(reminderService).updateReminder(reminderId, updateDto);
    }

    @Test
    void updateReminderInvalidRequest() {
        Long reminderId = 1L;
        ReminderUpdateRequestDto invalidDto = new ReminderUpdateRequestDto();

        when(reminderService.updateReminder(reminderId, invalidDto))
                .thenThrow(new IllegalArgumentException("type is required"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> controller.updateReminder(reminderId, invalidDto));

        assertEquals("type is required", exception.getMessage());
        verify(reminderService).updateReminder(reminderId, invalidDto);
    }

    @Test
    void deleteReminderSuccess() {
        Long reminderId = 1L;

        doNothing().when(reminderService).deleteReminder(reminderId);

        ResponseEntity<ApiResponseDto<String>> response = controller.deleteReminder(reminderId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Reminder deleted successfully", response.getBody().getMessage());
        verify(reminderService).deleteReminder(reminderId);
    }

    @Test
    void deleteReminderNotFound() {
        Long reminderId = 99L;

        doThrow(new NoSuchElementException("Reminder not found"))
                .when(reminderService).deleteReminder(reminderId);

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> controller.deleteReminder(reminderId));

        assertEquals("Reminder not found", exception.getMessage());
        verify(reminderService).deleteReminder(reminderId);
    }

    @Test
    void deleteReminderInternalServerError() {
        Long reminderId = 1L;

        doThrow(new RuntimeException("Internal server error"))
                .when(reminderService).deleteReminder(reminderId);

        Exception exception = assertThrows(RuntimeException.class,
                () -> controller.deleteReminder(reminderId));

        assertEquals("Internal server error", exception.getMessage());
        verify(reminderService).deleteReminder(reminderId);
    }

}
