package ru.neoflex.autoplanner.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = true)
    public List<RepairTypeResponseDto> getAll() {
        List<RepairType> types = repository.findAll();
        if (types.isEmpty()) throw new NoSuchElementException("Repair types not found");
        return types.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public RepairTypeResponseDto create(RepairTypeRequestDto dto) {

        RepairType type = new RepairType();
        type.setName(dto.getName());
        type.setCategory(dto.getCategory());

        return mapper.toDto(repository.save(type));
    }

    @Transactional
    public RepairTypeResponseDto update(Long id, RepairTypeRequestDto dto) {
        RepairType type = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Repair type not found"));

        type.setName(dto.getName());
        type.setCategory(dto.getCategory());

        return mapper.toDto(repository.save(type));
    }

    @Transactional
    public void delete(Long id) {
        RepairType type = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Repair type not found"));

        repository.delete(type);
    }
}