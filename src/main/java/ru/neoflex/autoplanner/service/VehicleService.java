package ru.neoflex.autoplanner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.neoflex.autoplanner.dto.VehicleCreateRequestDto;
import ru.neoflex.autoplanner.dto.VehicleResponseDto;
import ru.neoflex.autoplanner.dto.VehicleUpdateRequestDto;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.entity.Vehicle;
import ru.neoflex.autoplanner.mapper.VehicleMapper;
import ru.neoflex.autoplanner.repository.UserRepository;
import ru.neoflex.autoplanner.repository.VehicleRepository;



@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    public VehicleResponseDto createVehicle(VehicleCreateRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        Vehicle vehicle = vehicleMapper.toEntity(dto);
        vehicle.setUser(user);

        Vehicle saved = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(saved);
    }

    public VehicleResponseDto getVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        return vehicleMapper.toResponse(vehicle);
    }

    public VehicleResponseDto updateVehicle(Long id, VehicleUpdateRequestDto dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));

        vehicleMapper.updateEntityFromDto(vehicle, dto);
        Vehicle updated = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(updated);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + id));
        vehicleRepository.delete(vehicle);
    }
}