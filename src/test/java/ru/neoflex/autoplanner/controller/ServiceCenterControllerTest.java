package ru.neoflex.autoplanner.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.ServiceCenterService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCenterControllerTest {

    @Mock
    private ServiceCenterService service;

    @InjectMocks
    private ServiceCenterController controller;


    @Test
    void getByIdSuccess() {
        Long id = 1L;
        ServiceCenterResponseDto responseDto = new ServiceCenterResponseDto(
                id, "Service A", "123 Street", "+1234567890", BigDecimal.valueOf(4.5),
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(service.getById(id)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> response = controller.getById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getData().getId());
        verify(service).getById(id);
    }

    @Test
    void getByIdNotFound() {
        Long id = 99L;
        when(service.getById(id)).thenThrow(new NoSuchElementException("Service center not found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.getById(id));

        assertEquals("Service center not found", exception.getMessage());
        verify(service).getById(id);
    }

    @Test
    void createSuccess() {
        ServiceCenterRequestDto requestDto = new ServiceCenterRequestDto("Service A",
                "123 Street", "+1234567890", BigDecimal.valueOf(4.5));
        ServiceCenterResponseDto responseDto = new ServiceCenterResponseDto(
                1L, "Service A", "123 Street", "+1234567890", BigDecimal.valueOf(4.5),
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(service.create(requestDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> response = controller.create(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(responseDto.getId(), response.getBody().getData().getId());
        verify(service).create(requestDto);
    }

    @Test
    void createInvalidRequest() {
        ServiceCenterRequestDto invalidDto = new ServiceCenterRequestDto("", "", "", BigDecimal.valueOf(-1));

        when(service.create(invalidDto)).thenThrow(new IllegalArgumentException("name is required"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.create(invalidDto));

        assertEquals("name is required", exception.getMessage());
        verify(service).create(invalidDto);
    }

    @Test
    void updateSuccess() {
        Long id = 1L;
        ServiceCenterUpdateRequestDto updateDto = new ServiceCenterUpdateRequestDto("Service B",
                "456 Ave", "+0987654321", BigDecimal.valueOf(5.0));
        ServiceCenterResponseDto responseDto = new ServiceCenterResponseDto(
                id, "Service B", "456 Ave", "+0987654321", BigDecimal.valueOf(5.0),
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(service.update(id, updateDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<ServiceCenterResponseDto>> response = controller.update(id, updateDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getData().getId());
        verify(service).update(id, updateDto);
    }

    @Test
    void updateNotFound() {
        Long id = 99L;
        ServiceCenterUpdateRequestDto updateDto = new ServiceCenterUpdateRequestDto("Service B",
                "456 Ave", "+0987654321", BigDecimal.valueOf(5.0));

        when(service.update(id, updateDto)).thenThrow(new NoSuchElementException("Service center not found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.update(id, updateDto));

        assertEquals("Service center not found", exception.getMessage());
        verify(service).update(id, updateDto);
    }

    @Test
    void updateInvalidRequest() {
        Long id = 1L;
        ServiceCenterUpdateRequestDto invalidDto = new ServiceCenterUpdateRequestDto("", "", "", BigDecimal.valueOf(-1));
        when(service.update(id, invalidDto)).thenThrow(new IllegalArgumentException("name is required"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.update(id, invalidDto));

        assertEquals("name is required", exception.getMessage());
        verify(service).update(id, invalidDto);
    }

    @Test
    void deleteSuccess() {
        Long id = 1L;

        doNothing().when(service).delete(id);

        ResponseEntity<ApiResponseDto<String>> response = controller.delete(id);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Service center deleted successfully", response.getBody().getMessage());
        verify(service).delete(id);
    }

    @Test
    void deleteNotFound() {
        Long id = 99L;

        doThrow(new NoSuchElementException("Service center not found")).when(service).delete(id);
        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.delete(id));

        assertEquals("Service center not found", exception.getMessage());
        verify(service).delete(id);
    }

    @Test
    void deleteInternalServerError() {
        Long id = 1L;

        doThrow(new RuntimeException("Internal server error")).when(service).delete(id);

        Exception exception = assertThrows(RuntimeException.class, () -> controller.delete(id));

        assertEquals("Internal server error", exception.getMessage());
        verify(service).delete(id);
    }
}
