package ru.neoflex.autoplanner.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.autoplanner.dto.*;
import ru.neoflex.autoplanner.entity.*;
import ru.neoflex.autoplanner.entity.AppointmentRequest.AppointmentStatus;
import ru.neoflex.autoplanner.mapper.AppointmentRequestMapper;
import ru.neoflex.autoplanner.repository.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AppointmentRequestServiceTest {

    @Mock
    private AppointmentRequestRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private ServiceCenterRepository serviceCenterRepository;

    @Mock
    private AppointmentRequestMapper mapper;

    @InjectMocks
    private AppointmentRequestService service;

    private User user;
    private Vehicle vehicle;
    private ServiceCenter center;
    private AppointmentRequest request;
    private AppointmentRequestResponseDto responseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        vehicle = new Vehicle();
        vehicle.setId(2L);

        center = new ServiceCenter();
        center.setId(3L);

        request = new AppointmentRequest();
        request.setId(10L);
        request.setUser(user);
        request.setVehicle(vehicle);
        request.setServiceCenter(center);
        request.setServiceDate(LocalDateTime.now());
        request.setStatus(AppointmentStatus.PENDING);

        responseDto = new AppointmentRequestResponseDto();
    }

    @Test
    void getByUserIdSuccess() {
        when(repository.findByUserId(1L)).thenReturn(List.of(request));
        when(mapper.toDto(request)).thenReturn(responseDto);

        List<AppointmentRequestResponseDto> result = service.getByUserId(1L);

        assertEquals(1, result.size());
        verify(repository).findByUserId(1L);
    }

    @Test
    void getByUserIdEmpty() {
        when(repository.findByUserId(1L)).thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementException.class,
                () -> service.getByUserId(1L));
    }

    @Test
    void createSuccess() {
        AppointmentRequestRequestDto dto = new AppointmentRequestRequestDto();
        dto.setUserId(1L);
        dto.setVehicleId(2L);
        dto.setServiceCenterId(3L);
        dto.setStatus("pending");
        dto.setRequestedDate(LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(vehicleRepository.findById(2L)).thenReturn(Optional.of(vehicle));
        when(serviceCenterRepository.findById(3L)).thenReturn(Optional.of(center));
        when(repository.save(any(AppointmentRequest.class))).thenReturn(request);
        when(mapper.toDto(request)).thenReturn(responseDto);

        AppointmentRequestResponseDto result = service.create(dto);

        assertNotNull(result);
        verify(repository).save(any(AppointmentRequest.class));
    }

    @Test
    void createUserNotFound() {
        AppointmentRequestRequestDto dto = new AppointmentRequestRequestDto();
        dto.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.create(dto));
    }

    @Test
    void createVehicleNotFound() {
        AppointmentRequestRequestDto dto = new AppointmentRequestRequestDto();
        dto.setUserId(1L);
        dto.setVehicleId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(vehicleRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.create(dto));
    }

    @Test
    void createServiceCenterNotFound() {
        AppointmentRequestRequestDto dto = new AppointmentRequestRequestDto();
        dto.setUserId(1L);
        dto.setVehicleId(2L);
        dto.setServiceCenterId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(vehicleRepository.findById(2L)).thenReturn(Optional.of(vehicle));
        when(serviceCenterRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.create(dto));
    }

    @Test
    void updateSuccess() {
        AppointmentRequestUpdateDto dto = new AppointmentRequestUpdateDto();
        dto.setStatus("confirmed");
        dto.setComment("updated");

        when(repository.findById(10L)).thenReturn(Optional.of(request));
        when(repository.save(request)).thenReturn(request);
        when(mapper.toDto(request)).thenReturn(responseDto);

        AppointmentRequestResponseDto result = service.update(10L, dto);

        assertNotNull(result);
        verify(repository).save(request);
    }

    @Test
    void updateNotFound() {
        when(repository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.update(10L, new AppointmentRequestUpdateDto()));
    }

    @Test
    void deleteSuccess() {
        when(repository.findById(10L)).thenReturn(Optional.of(request));

        service.delete(10L);

        verify(repository).delete(request);
    }

    @Test
    void deleteNotFound() {
        when(repository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> service.delete(10L));
    }
}
