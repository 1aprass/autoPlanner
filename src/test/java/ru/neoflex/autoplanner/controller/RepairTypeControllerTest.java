package ru.neoflex.autoplanner.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.RepairTypeService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepairTypeControllerTest {

    @Mock
    private RepairTypeService service;

    @InjectMocks
    private RepairTypeController controller;


    @Test
    void getAllSuccess() {
        List<RepairTypeResponseDto> list = List.of(
                new RepairTypeResponseDto(1L, "Oil Change", "Maintenance",
                        LocalDateTime.now(), LocalDateTime.now())
        );

        when(service.getAll()).thenReturn(list);

        ResponseEntity<ApiResponseDto<List<RepairTypeResponseDto>>> response = controller.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        verify(service).getAll();
    }

    @Test
    void getAllNotFound() {
        when(service.getAll()).thenThrow(new NoSuchElementException("Repair types not found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.getAll());

        assertEquals("Repair types not found", exception.getMessage());
        verify(service).getAll();
    }

    @Test
    void createSuccess() {
        RepairTypeRequestDto requestDto = new RepairTypeRequestDto("Oil Change", "Maintenance");
        RepairTypeResponseDto responseDto = new RepairTypeResponseDto(
                1L, "Oil Change", "Maintenance", LocalDateTime.now(), LocalDateTime.now()
        );

        when(service.create(requestDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<RepairTypeResponseDto>> response = controller.create(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(responseDto.getId(), response.getBody().getData().getId());
        verify(service).create(requestDto);
    }

    @Test
    void createInvalidRequest() {
        RepairTypeRequestDto invalidDto = new RepairTypeRequestDto("", "");

        when(service.create(invalidDto)).thenThrow(new IllegalArgumentException("name is required"));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> controller.create(invalidDto));

        assertEquals("name is required", exception.getMessage());
        verify(service).create(invalidDto);
    }

    @Test
    void updateSuccess() {
        Long id = 1L;
        RepairTypeRequestDto updateDto = new RepairTypeRequestDto("Tire Replacement", "Repair");
        RepairTypeResponseDto responseDto = new RepairTypeResponseDto(
                id, "Tire Replacement", "Repair", LocalDateTime.now(), LocalDateTime.now()
        );

        when(service.update(id, updateDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<RepairTypeResponseDto>> response = controller.update(id, updateDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getData().getId());
        verify(service).update(id, updateDto);
    }

    @Test
    void updateNotFound() {
        Long id = 99L;
        RepairTypeRequestDto updateDto = new RepairTypeRequestDto("Tire Replacement", "Repair");

        when(service.update(id, updateDto)).thenThrow(new NoSuchElementException("Repair type not found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.update(id, updateDto));

        assertEquals("Repair type not found", exception.getMessage());
        verify(service).update(id, updateDto);
    }

    @Test
    void updateInvalidRequest() {
        Long id = 1L;
        RepairTypeRequestDto invalidDto = new RepairTypeRequestDto("", "");

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
        assertEquals("Repair type deleted successfully", response.getBody().getMessage());
        verify(service).delete(id);
    }

    @Test
    void deleteNotFound() {
        Long id = 99L;

        doThrow(new NoSuchElementException("Repair type not found")).when(service).delete(id);

        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.delete(id));

        assertEquals("Repair type not found", exception.getMessage());
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