package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.autoplanner.dto.VehicleCreateRequestDto;
import ru.neoflex.autoplanner.dto.VehicleResponseDto;
import ru.neoflex.autoplanner.dto.VehicleUpdateRequestDto;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.entity.Vehicle;
import ru.neoflex.autoplanner.mapper.VehicleMapper;
import ru.neoflex.autoplanner.repository.UserRepository;
import ru.neoflex.autoplanner.repository.VehicleRepository;

import java.util.NoSuchElementException;


@RequiredArgsConstructor
@Service
public class VehicleService {


    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public VehicleResponseDto createVehicle(VehicleCreateRequestDto dto) {
        User user = getUserOrThrow(dto.getUserId());
        Vehicle vehicle = vehicleMapper.toEntity(dto);
        vehicle.setUser(user);

        Vehicle saved = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public VehicleResponseDto getVehicle(Long id) {
        Vehicle vehicle = getVehicleOrThrow(id);
        return vehicleMapper.toResponse(vehicle);
    }

    @Transactional
    public VehicleResponseDto updateVehicle(Long id, VehicleUpdateRequestDto dto) {
        Vehicle vehicle = getVehicleOrThrow(id);

        vehicleMapper.updateEntityFromDto(vehicle, dto);
        Vehicle updated = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(updated);
    }
    @Transactional
    public void deleteVehicle(Long id) {
        Vehicle vehicle = getVehicleOrThrow(id);
        vehicleRepository.delete(vehicle);
    }

    private Vehicle getVehicleOrThrow(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NoSuchElementException("Vehicle not found with id: " + vehicleId));
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));
    }
}