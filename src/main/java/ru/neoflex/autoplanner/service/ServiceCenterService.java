package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.autoplanner.dto.ServiceCenterRequestDto;
import ru.neoflex.autoplanner.dto.ServiceCenterResponseDto;
import ru.neoflex.autoplanner.dto.ServiceCenterUpdateRequestDto;
import ru.neoflex.autoplanner.entity.ServiceCenter;
import ru.neoflex.autoplanner.mapper.ServiceCenterMapper;
import ru.neoflex.autoplanner.repository.ServiceCenterRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceCenterService {

    private final ServiceCenterRepository repository;
    private final ServiceCenterMapper mapper;

    public ServiceCenterResponseDto getById(Long id) {
        ServiceCenter entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service center not found"));
        return mapper.toDto(entity);
    }

    public ServiceCenterResponseDto create(ServiceCenterRequestDto dto) {
        boolean exists = repository.findByNameContainingIgnoreCase(dto.getName()).stream()
                .anyMatch(center -> center.getAddress().equalsIgnoreCase(dto.getAddress()));
        if (exists) {
            throw new RuntimeException("Service center with this name and address already exists");
        }
        ServiceCenter saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    public ServiceCenterResponseDto update(Long id, ServiceCenterUpdateRequestDto dto) {
        ServiceCenter center = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service center not found"));
        mapper.updateFromDto(dto, center);
        return mapper.toDto(repository.save(center));
    }

    public void delete(Long id) {
        ServiceCenter center = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service center not found"));
        repository.delete(center);
    }
}