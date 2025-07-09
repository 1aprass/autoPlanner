package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public List<AppointmentRequestResponseDto> getByUserId(Long userId) {

        List<AppointmentRequest> list = repository.findByUserId(userId);
        if (list.isEmpty()) throw new NoSuchElementException("No appointment requests found for this user");

        return list.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public AppointmentRequestResponseDto create(AppointmentRequestRequestDto dto) {
        User user = getUserOrThrow(dto.getUserId());
        Vehicle vehicle = getVehicleOrThrow(dto.getVehicleId());
        ServiceCenter center = getServiceCenterOrThrow(dto.getServiceCenterId());

        AppointmentRequest request = new AppointmentRequest();
        request.setUser(user);
        request.setVehicle(vehicle);
        request.setServiceCenter(center);
        request.setServiceDate(dto.getRequestedDate());
        request.setStatus(AppointmentStatus.valueOf(dto.getStatus().toUpperCase()));
        request.setComments(dto.getComment());

        return mapper.toDto(repository.save(request));
    }

    @Transactional
    public AppointmentRequestResponseDto update(Long id, AppointmentRequestUpdateDto dto) {
        AppointmentRequest request = getAppointmentRequestOrThrow(id);

        request.setStatus(AppointmentStatus.valueOf(dto.getStatus().toUpperCase()));
        request.setComments(dto.getComment());

        return mapper.toDto(repository.save(request));
    }

    @Transactional
    public void delete(Long id) {
        AppointmentRequest request = getAppointmentRequestOrThrow(id);
        repository.delete(request);
    }


    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    private Vehicle getVehicleOrThrow(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NoSuchElementException("Vehicle not found"));
    }

    private ServiceCenter getServiceCenterOrThrow(Long centerId) {
        return serviceCenterRepository.findById(centerId)
                .orElseThrow(() -> new NoSuchElementException("Service center not found"));
    }

    private AppointmentRequest getAppointmentRequestOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Appointment request not found"));
    }
}

