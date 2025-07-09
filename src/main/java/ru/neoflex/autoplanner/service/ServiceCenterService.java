package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.neoflex.autoplanner.dto.ServiceCenterRequestDto;
import ru.neoflex.autoplanner.dto.ServiceCenterResponseDto;
import ru.neoflex.autoplanner.dto.ServiceCenterUpdateRequestDto;
import ru.neoflex.autoplanner.entity.ServiceCenter;
import ru.neoflex.autoplanner.mapper.ServiceCenterMapper;
import ru.neoflex.autoplanner.repository.ServiceCenterRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceCenterService {

    private final ServiceCenterRepository repository;
    private final ServiceCenterMapper mapper;

    @Transactional(readOnly = true)
    public ServiceCenterResponseDto getById(Long id) {
        ServiceCenter entity = getServiceCenterOrThrow(id);
        return mapper.toDto(entity);
    }

    @Transactional
    public ServiceCenterResponseDto create(ServiceCenterRequestDto dto) {
        boolean exists = repository.findByNameContainingIgnoreCase(dto.getName()).stream()
                .anyMatch(center -> center.getAddress().equalsIgnoreCase(dto.getAddress()));
        if (exists) {
            throw new IllegalStateException("Service center with this name and address already exists");
        }
        ServiceCenter saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Transactional
    public ServiceCenterResponseDto update(Long id, ServiceCenterUpdateRequestDto dto) {
        ServiceCenter center = getServiceCenterOrThrow(id);
        mapper.updateFromDto(dto, center);
        return mapper.toDto(repository.save(center));
    }

    @Transactional
    public void delete(Long id) {
        ServiceCenter center = getServiceCenterOrThrow(id);
        repository.delete(center);
    }

    private ServiceCenter getServiceCenterOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Service center not found with id: " + id));
    }
}