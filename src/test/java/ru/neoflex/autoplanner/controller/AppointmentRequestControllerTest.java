package ru.neoflex.autoplanner.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.AppointmentRequestService;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentRequestControllerTest {

    @Mock
    private AppointmentRequestService service;

    @InjectMocks
    private AppointmentRequestController controller;


    @Test
    void getByUserIdSuccess() {
        Long userId = 1L;
        List<AppointmentRequestResponseDto> requests = List.of(
                new AppointmentRequestResponseDto(1L, userId, 2L, 3L,
                        LocalDateTime.now(), "PENDING", "comment", LocalDateTime.now(), LocalDateTime.now())
        );

        when(service.getByUserId(userId)).thenReturn(requests);

        ResponseEntity<ApiResponseDto<List<AppointmentRequestResponseDto>>> response = controller.getByUserId(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        verify(service).getByUserId(userId);
    }

    @Test
    void getByUserIdNotFound() {
        Long userId = 99L;

        when(service.getByUserId(userId)).thenThrow(new NoSuchElementException("No request found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.getByUserId(userId));

        assertEquals("No request found", exception.getMessage());
        verify(service).getByUserId(userId);
    }

    @Test
    void createSuccess() {
        AppointmentRequestRequestDto requestDto = new AppointmentRequestRequestDto(
                1L, 2L, 3L, LocalDateTime.now(), "PENDING", "comment"
        );
        AppointmentRequestResponseDto responseDto = new AppointmentRequestResponseDto(
                1L, 1L, 2L, 3L, LocalDateTime.now(),
                "PENDING", "comment", LocalDateTime.now(), LocalDateTime.now()
        );

        when(service.create(requestDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<AppointmentRequestResponseDto>> response = controller.create(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(responseDto.getId(), response.getBody().getData().getId());
        verify(service).create(requestDto);
    }

    @Test
    void createInvalidRequest() {
        AppointmentRequestRequestDto invalidDto = new AppointmentRequestRequestDto(
                null, 2L, 3L, LocalDateTime.now(), "PENDING", "comment"
        );

        when(service.create(invalidDto)).thenThrow(new IllegalArgumentException("userId is required"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.create(invalidDto));

        assertEquals("userId is required", exception.getMessage());
        verify(service).create(invalidDto);
    }

    @Test
    void updateSuccess() {
        Long requestId = 1L;
        AppointmentRequestUpdateDto updateDto = new AppointmentRequestUpdateDto("CONFIRMED", "updated comment");
        AppointmentRequestResponseDto responseDto = new AppointmentRequestResponseDto(
                requestId, 1L, 2L, 3L, LocalDateTime.now(),
                "CONFIRMED", "updated comment", LocalDateTime.now(), LocalDateTime.now()
        );

        when(service.update(requestId, updateDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<AppointmentRequestResponseDto>> response = controller.update(requestId, updateDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(requestId, response.getBody().getData().getId());
        verify(service).update(requestId, updateDto);
    }

    @Test
    void updateNotFound() {
        Long requestId = 99L;
        AppointmentRequestUpdateDto updateDto = new AppointmentRequestUpdateDto("CONFIRMED", "updated comment");

        when(service.update(requestId, updateDto)).thenThrow(new NoSuchElementException("Request not found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.update(requestId, updateDto));

        assertEquals("Request not found", exception.getMessage());
        verify(service).update(requestId, updateDto);
    }

    @Test
    void updateInvalidRequest() {
        Long requestId = 1L;
        AppointmentRequestUpdateDto invalidDto = new AppointmentRequestUpdateDto("", "updated comment");

        when(service.update(requestId, invalidDto)).thenThrow(new IllegalArgumentException("status is required"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.update(requestId, invalidDto));

        assertEquals("status is required", exception.getMessage());
        verify(service).update(requestId, invalidDto);
    }

    @Test
    void deleteSuccess() {
        Long requestId = 1L;

        doNothing().when(service).delete(requestId);

        ResponseEntity<ApiResponseDto<String>> response = controller.delete(requestId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Appointment request deleted successfully", response.getBody().getMessage());
        verify(service).delete(requestId);
    }

    @Test
    void deleteNotFound() {
        Long requestId = 99L;

        doThrow(new NoSuchElementException("Request not found")).when(service).delete(requestId);

        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.delete(requestId));

        assertEquals("Request not found", exception.getMessage());
        verify(service).delete(requestId);
    }

    @Test
    void deleteInternalServerError() {
        Long requestId = 1L;

        doThrow(new RuntimeException("Internal server error")).when(service).delete(requestId);

        Exception exception = assertThrows(RuntimeException.class, () -> controller.delete(requestId));

        assertEquals("Internal server error", exception.getMessage());
        verify(service).delete(requestId);
    }
}