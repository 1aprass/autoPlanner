package ru.neoflex.autoplanner.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.ServiceCenterRepairTypeService;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceCenterRepairTypeControllerTest {

    @Mock
    private ServiceCenterRepairTypeService service;

    @InjectMocks
    private ServiceCenterRepairTypeController controller;



    @Test
    void getByServiceCenterSuccess() {
        Long serviceCenterId = 1L;
        List<ServiceCenterRepairTypeResponseDto> services = List.of(
                new ServiceCenterRepairTypeResponseDto(serviceCenterId, 2L, "Oil Change", 500, "RUB")
        );

        when(service.getByServiceCenter(serviceCenterId)).thenReturn(services);

        ResponseEntity<ApiResponseDto<List<ServiceCenterRepairTypeResponseDto>>> response =
                controller.getByServiceCenter(serviceCenterId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getData().size());
        verify(service).getByServiceCenter(serviceCenterId);
    }

    @Test
    void getByServiceCenterNotFound() {
        Long serviceCenterId = 99L;
        when(service.getByServiceCenter(serviceCenterId))
                .thenThrow(new NoSuchElementException("Services not found"));

        Exception exception = assertThrows(NoSuchElementException.class,
                () -> controller.getByServiceCenter(serviceCenterId));

        assertEquals("Services not found", exception.getMessage());
        verify(service).getByServiceCenter(serviceCenterId);
    }

    @Test
    void addSuccess() {
        ServiceCenterRepairTypeRequestDto requestDto = new ServiceCenterRepairTypeRequestDto(1L,
                2L, 500, "RUB");
        ServiceCenterRepairTypeResponseDto responseDto =
                new ServiceCenterRepairTypeResponseDto(1L, 2L, "Oil Change", 500, "RUB");

        when(service.add(requestDto)).thenReturn(responseDto);

        ResponseEntity<ApiResponseDto<ServiceCenterRepairTypeResponseDto>> response = controller.add(requestDto);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2L, response.getBody().getData().getRepairTypeId());
        verify(service).add(requestDto);
    }

    @Test
    void addInvalidRequest() {
        ServiceCenterRepairTypeRequestDto invalidDto = new ServiceCenterRepairTypeRequestDto(null,
                null, 0, "");

        when(service.add(invalidDto)).thenThrow(new IllegalArgumentException("serviceCenterId is required"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.add(invalidDto));

        assertEquals("serviceCenterId is required", exception.getMessage());
        verify(service).add(invalidDto);
    }

    @Test
    void deleteSuccess() {
        Long id = 1L;

        doNothing().when(service).delete(id);

        ResponseEntity<ApiResponseDto<String>> response = controller.delete(id);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Service center repair type deleted successfully", response.getBody().getMessage());
        verify(service).delete(id);
    }

    @Test
    void deleteNotFound() {
        Long id = 99L;

        doThrow(new NoSuchElementException("Service with the specified ID not found")).when(service).delete(id);

        Exception exception = assertThrows(NoSuchElementException.class, () -> controller.delete(id));

        assertEquals("Service with the specified ID not found", exception.getMessage());
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
