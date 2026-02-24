package ru.neoflex.autoplanner.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.neoflex.autoplanner.dto.VehicleCreateRequestDto;
import ru.neoflex.autoplanner.dto.VehicleResponseDto;
import ru.neoflex.autoplanner.dto.VehicleUpdateRequestDto;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.entity.Vehicle;
import ru.neoflex.autoplanner.mapper.VehicleMapper;
import ru.neoflex.autoplanner.repository.UserRepository;
import ru.neoflex.autoplanner.repository.VehicleRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void createVehicleSuccessfully(){
        Long userId = 1L;

        VehicleCreateRequestDto requestDto = new VehicleCreateRequestDto();
        requestDto.setUserId(userId);

        User user = new User();
        user.setId(userId);

        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");

        Vehicle savedVehicle = new Vehicle();
        savedVehicle.setId(10L);
        savedVehicle.setMake("Toyota");

        VehicleResponseDto responseDto = new VehicleResponseDto();
        responseDto.setId(10L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(vehicleMapper.toEntity(requestDto)).thenReturn(vehicle);
        when(vehicleRepository.save(vehicle)).thenReturn(savedVehicle);
        when(vehicleMapper.toResponse(savedVehicle)).thenReturn(responseDto);

        VehicleResponseDto result = vehicleService.createVehicle(requestDto);

        assertNotNull(result);
        assertEquals(10L, result.getId());

        verify(userRepository).findById(userId);
        verify(vehicleMapper).toEntity(requestDto);
        verify(vehicleRepository).save(vehicle);
        verify(vehicleMapper).toResponse(savedVehicle);
    }

    @Test
    void createVehicleUnsuccessful(){
        Long userId = 1L;
        VehicleCreateRequestDto dto = new VehicleCreateRequestDto();
        dto.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                () -> vehicleService.createVehicle(dto));

        verify(userRepository).findById(userId);
        verifyNoInteractions(vehicleRepository);
    }

    @Test
    void deleteVehicleSuccessful() {

        Long vehicleId = 1L;

        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));

        vehicleService.deleteVehicle(vehicleId);

        verify(vehicleRepository).findById(vehicleId);
        verify(vehicleRepository).delete(vehicle);
    }

    @Test
    void deleteVehicleUnSuccessfully() {

        Long vehicleId = 1L;

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> vehicleService.deleteVehicle(vehicleId));

        verify(vehicleRepository).findById(vehicleId);

    }

    @Test
    void getVehicleSuccessful(){
        Long vehicleId = 1L;

        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);

        VehicleResponseDto responseDto = new VehicleResponseDto();
        responseDto.setId(vehicleId);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(vehicleMapper.toResponse(vehicle)).thenReturn(responseDto);

        VehicleResponseDto result = vehicleService.getVehicle(vehicleId);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(vehicleRepository).findById(vehicleId);
        verify(vehicleMapper).toResponse(vehicle);


    }

    @Test
    void getVehicleUnSuccessful(){

        Long vehicleId = 1L;

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> vehicleService.getVehicle(vehicleId));

        verify(vehicleRepository).findById(vehicleId);
        verifyNoInteractions(vehicleMapper);


    }

    @Test
    void updateVehicleSuccessful(){

        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);

        VehicleUpdateRequestDto dto = new VehicleUpdateRequestDto();
        dto.setMake("Honda");

        Vehicle updatedVehicle = new Vehicle();
        updatedVehicle.setId(vehicleId);
        updatedVehicle.setMake("Honda");

        VehicleResponseDto responseDto = new VehicleResponseDto();
        responseDto.setId(vehicleId);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(vehicle)).thenReturn(updatedVehicle);
        when(vehicleMapper.toResponse(updatedVehicle)).thenReturn(responseDto);

        VehicleResponseDto result = vehicleService.updateVehicle(vehicleId, dto);


        assertNotNull(result);
        assertEquals(vehicleId, result.getId());

        verify(vehicleRepository).findById(vehicleId);
        verify(vehicleMapper).updateEntityFromDto(vehicle, dto);
        verify(vehicleRepository).save(vehicle);
        verify(vehicleMapper).toResponse(updatedVehicle);


    }

    @Test
    void updateVehicleUnSuccessful(){

        Long vehicleId = 1L;
        VehicleUpdateRequestDto dto = new VehicleUpdateRequestDto();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> vehicleService.updateVehicle(vehicleId, dto));

        verify(vehicleRepository).findById(vehicleId);
        verifyNoInteractions(vehicleMapper);
        verify(vehicleRepository, never()).save(any());


    }
}
