package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.autoplanner.dto.RepairTypeRequestDto;
import ru.neoflex.autoplanner.dto.RepairTypeResponseDto;
import ru.neoflex.autoplanner.entity.RepairType;
import ru.neoflex.autoplanner.mapper.RepairTypeMapper;
import ru.neoflex.autoplanner.repository.RepairTypeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepairTypeService {

    private final RepairTypeRepository repository;
    private final RepairTypeMapper mapper;

    public List<RepairTypeResponseDto> getAll() {
        List<RepairType> types = repository.findAll();
        if (types.isEmpty()) throw new NoSuchElementException("Repair types not found");
        return types.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public RepairTypeResponseDto create(RepairTypeRequestDto dto) {
        if (dto.getName() == null || dto.getCategory() == null) {
            throw new IllegalArgumentException("Name and category are required");
        }

        RepairType type = new RepairType();
        type.setName(dto.getName());
        type.setCategory(dto.getCategory());

        return mapper.toDto(repository.save(type));
    }

    public RepairTypeResponseDto update(Long id, RepairTypeRequestDto dto) {
        RepairType type = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Repair type not found"));

        type.setName(dto.getName());
        type.setCategory(dto.getCategory());

        return mapper.toDto(repository.save(type));
    }

    public void delete(Long id) {
        RepairType type = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Repair type not found"));

        repository.delete(type);
    }
}