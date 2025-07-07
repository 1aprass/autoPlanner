package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.autoplanner.dto.ServiceCenterRepairTypeRequestDto;
import ru.neoflex.autoplanner.dto.ServiceCenterRepairTypeResponseDto;
import ru.neoflex.autoplanner.entity.RepairType;
import ru.neoflex.autoplanner.entity.ServiceCenter;
import ru.neoflex.autoplanner.entity.ServiceCenterRepairType;
import ru.neoflex.autoplanner.mapper.ServiceCenterRepairTypeMapper;
import ru.neoflex.autoplanner.repository.RepairTypeRepository;
import ru.neoflex.autoplanner.repository.ServiceCenterRepairTypeRepository;
import ru.neoflex.autoplanner.repository.ServiceCenterRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceCenterRepairTypeService {

    private final ServiceCenterRepairTypeRepository repository;
    private final ServiceCenterRepository serviceCenterRepository;
    private final RepairTypeRepository repairTypeRepository;
    private final ServiceCenterRepairTypeMapper mapper;

    public List<ServiceCenterRepairTypeResponseDto> getByServiceCenter(Long serviceCenterId) {
        if (serviceCenterId == null) {
            throw new IllegalArgumentException("service_center_id is required");
        }

        List<ServiceCenterRepairType> entries = repository.findByServiceCenter_Id(serviceCenterId);

        if (entries.isEmpty()) {
            throw new NoSuchElementException("No services found for this service center");
        }

        return entries.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public ServiceCenterRepairTypeResponseDto add(ServiceCenterRepairTypeRequestDto dto) {
        validateDto(dto);

        Optional<ServiceCenterRepairType> existing = repository
                .findByServiceCenter_IdAndRepairType_Id(dto.getServiceCenterId(), dto.getRepairTypeId());

        if (existing.isPresent()) {
            throw new IllegalStateException("This repair type is already assigned to the service center");
        }

        ServiceCenter serviceCenter = serviceCenterRepository.findById(dto.getServiceCenterId())
                .orElseThrow(() -> new NoSuchElementException("Service center not found"));

        RepairType repairType = repairTypeRepository.findById(dto.getRepairTypeId())
                .orElseThrow(() -> new NoSuchElementException("Repair type not found"));

        ServiceCenterRepairType entity = new ServiceCenterRepairType();
        entity.setServiceCenter(serviceCenter);
        entity.setRepairType(repairType);

        ServiceCenterRepairType saved = repository.save(entity);

        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        ServiceCenterRepairType entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Service center repair type not found"));
        repository.delete(entity);
    }

    private void validateDto(ServiceCenterRepairTypeRequestDto dto) {
        if (dto.getServiceCenterId() == null || dto.getRepairTypeId() == null) {
            throw new IllegalArgumentException("Both service_center_id and repair_type_id are required");
        }
    }
}
