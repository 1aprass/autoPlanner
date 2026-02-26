package ru.neoflex.autoplanner.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.service.VehicleService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController controller;


    @Test
    void createVehicleSuccess() {
        VehicleCreateRequestDto request = new VehicleCreateRequestDto(
                "Toyota", "Camry", 2020, "ABC123", 15000, 1L
        );
        VehicleResponseDto response = new VehicleResponseDto(
                1L, 1L, "Toyota", "Camry", 2020, "ABC123", 15000,
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(vehicleService.createVehicle(request)).thenReturn(response);

        ResponseEntity<ApiResponseDto<VehicleResponseDto>> result = controller.createVehicle(request);

        assertEquals(201, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals("Toyota", result.getBody().getData().getMake());
        verify(vehicleService).createVehicle(request);
    }

    @Test
    void createVehicleUserNotFound() {
        VehicleCreateRequestDto request = new VehicleCreateRequestDto(
                "Toyota", "Camry", 2020, "ABC123", 15000, 99L
        );

        when(vehicleService.createVehicle(request))
                .thenThrow(new NoSuchElementException("User with the specified user_id not found"));

        Exception ex = assertThrows(NoSuchElementException.class,
                () -> controller.createVehicle(request));

        assertEquals("User with the specified user_id not found", ex.getMessage());
        verify(vehicleService).createVehicle(request);
    }

    @Test
    void getVehicleSuccess() {
        Long vehicleId = 1L;
        VehicleResponseDto response = new VehicleResponseDto(
                vehicleId, 1L, "Toyota", "Camry", 2020, "ABC123", 15000,
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(vehicleService.getVehicle(vehicleId)).thenReturn(response);

        ResponseEntity<ApiResponseDto<VehicleResponseDto>> result = controller.getVehicle(vehicleId);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(vehicleId, result.getBody().getData().getId());
        verify(vehicleService).getVehicle(vehicleId);
    }

    @Test
    void getVehicleNotFound() {
        Long vehicleId = 99L;

        when(vehicleService.getVehicle(vehicleId))
                .thenThrow(new NoSuchElementException("Vehicle with specified ID not found"));

        Exception ex = assertThrows(NoSuchElementException.class,
                () -> controller.getVehicle(vehicleId));

        assertEquals("Vehicle with specified ID not found", ex.getMessage());
        verify(vehicleService).getVehicle(vehicleId);
    }

    @Test
    void updateVehicleSuccess() {
        Long vehicleId = 1L;
        VehicleUpdateRequestDto update = new VehicleUpdateRequestDto(
                "Toyota", "Corolla", 2021, "XYZ789", 20000
        );
        VehicleResponseDto response = new VehicleResponseDto(
                vehicleId, 1L, "Toyota", "Corolla", 2021, "XYZ789", 20000,
                LocalDateTime.now(), LocalDateTime.now()
        );

        when(vehicleService.updateVehicle(vehicleId, update)).thenReturn(response);

        ResponseEntity<ApiResponseDto<VehicleResponseDto>> result = controller.updateVehicle(vehicleId, update);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals("Corolla", result.getBody().getData().getModel());
        verify(vehicleService).updateVehicle(vehicleId, update);
    }

    @Test
    void updateVehicleNotFound() {
        Long vehicleId = 99L;
        VehicleUpdateRequestDto update = new VehicleUpdateRequestDto(
                "Toyota", "Corolla", 2021, "XYZ789", 20000
        );
        when(vehicleService.updateVehicle(vehicleId, update))
                .thenThrow(new NoSuchElementException("Vehicle with specified ID not found"));

        Exception ex = assertThrows(NoSuchElementException.class,
                () -> controller.updateVehicle(vehicleId, update));

        assertEquals("Vehicle with specified ID not found", ex.getMessage());
        verify(vehicleService).updateVehicle(vehicleId, update);
    }

    @Test
    void deleteVehicleSuccess() {
        Long vehicleId = 1L;

        doNothing().when(vehicleService).deleteVehicle(vehicleId);

        ResponseEntity<ApiResponseDto<String>> result = controller.deleteVehicle(vehicleId);

        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals("Vehicle deleted successfully", result.getBody().getMessage());
        verify(vehicleService).deleteVehicle(vehicleId);
    }

    @Test
    void deleteVehicleNotFound() {
        Long vehicleId = 99L;

        doThrow(new NoSuchElementException("Vehicle with specified ID not found"))
                .when(vehicleService).deleteVehicle(vehicleId);

        Exception ex = assertThrows(NoSuchElementException.class,
                () -> controller.deleteVehicle(vehicleId));

        assertEquals("Vehicle with specified ID not found", ex.getMessage());
        verify(vehicleService).deleteVehicle(vehicleId);
    }

    @Test
    void deleteVehicleInternalError() {
        Long vehicleId = 1L;

        doThrow(new RuntimeException("Internal Server Error"))
                .when(vehicleService).deleteVehicle(vehicleId);
        Exception ex = assertThrows(RuntimeException.class,
                () -> controller.deleteVehicle(vehicleId));

        assertEquals("Internal Server Error", ex.getMessage());
        verify(vehicleService).deleteVehicle(vehicleId);
    }
}