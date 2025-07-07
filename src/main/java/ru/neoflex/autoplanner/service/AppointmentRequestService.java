package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.autoplanner.dto.AppointmentRequestRequestDto;
import ru.neoflex.autoplanner.dto.AppointmentRequestResponseDto;
import ru.neoflex.autoplanner.dto.AppointmentRequestUpdateDto;
import ru.neoflex.autoplanner.entity.AppointmentRequest;
import ru.neoflex.autoplanner.entity.AppointmentRequest.AppointmentStatus;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.entity.Vehicle;
import ru.neoflex.autoplanner.entity.ServiceCenter;
import ru.neoflex.autoplanner.mapper.AppointmentRequestMapper;
import ru.neoflex.autoplanner.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentRequestService {

    private final AppointmentRequestRepository repository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final ServiceCenterRepository serviceCenterRepository;
    private final AppointmentRequestMapper mapper;

    public List<AppointmentRequestResponseDto> getByUserId(Long userId) {
        if (userId == null) throw new IllegalArgumentException("user_id is required");

        List<AppointmentRequest> list = repository.findByUserId(userId);
        if (list.isEmpty()) throw new NoSuchElementException("No appointment requests found for this user");

        return list.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public AppointmentRequestResponseDto create(AppointmentRequestRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new NoSuchElementException("Vehicle not found"));
        ServiceCenter center = serviceCenterRepository.findById(dto.getServiceCenterId())
                .orElseThrow(() -> new NoSuchElementException("Service center not found"));

        AppointmentRequest request = new AppointmentRequest();
        request.setUser(user);
        request.setVehicle(vehicle);
        request.setServiceCenter(center);
        request.setServiceDate(dto.getRequestedDate());
        request.setStatus(AppointmentStatus.valueOf(dto.getStatus().toUpperCase()));
        request.setComments(dto.getComment());

        return mapper.toDto(repository.save(request));
    }

    public AppointmentRequestResponseDto update(Long id, AppointmentRequestUpdateDto dto) {
        AppointmentRequest request = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Appointment request not found"));

        request.setStatus(AppointmentStatus.valueOf(dto.getStatus().toUpperCase()));
        request.setComments(dto.getComment());

        return mapper.toDto(repository.save(request));
    }

    public void delete(Long id) {
        AppointmentRequest request = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Appointment request not found"));
        repository.delete(request);
    }
}