package ru.neoflex.autoplanner.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotRequestDto;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotResponseDto;
import ru.neoflex.autoplanner.dto.AnalyticsSnapshotUpdateDto;
import ru.neoflex.autoplanner.entity.AnalyticsSnapshot;
import ru.neoflex.autoplanner.entity.RepairType;
import ru.neoflex.autoplanner.entity.User;
import ru.neoflex.autoplanner.mapper.AnalyticsSnapshotMapper;
import ru.neoflex.autoplanner.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsSnapshotService {

    private final AnalyticsSnapshotRepository repository;
    private final UserRepository userRepository;
    private final RepairTypeRepository repairTypeRepository;
    private final AnalyticsSnapshotMapper mapper;

    @Transactional(readOnly = true)
    public List<AnalyticsSnapshotResponseDto> getByUserId(Long userId) {

        if (userId == null) throw new IllegalArgumentException("user_id is required");

        List<AnalyticsSnapshot> snapshots = repository.findByUserId(userId);
        if (snapshots.isEmpty()) throw new NoSuchElementException("No analytics found for this user");

        return snapshots.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public AnalyticsSnapshotResponseDto create(AnalyticsSnapshotRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        RepairType repairType = null;
        if (dto.getMostCommonRepairTypeId() != null) {
            repairType = repairTypeRepository.findById(dto.getMostCommonRepairTypeId())
                    .orElseThrow(() -> new NoSuchElementException("Repair type not found"));
        }

        AnalyticsSnapshot snapshot = new AnalyticsSnapshot();
        snapshot.setUser(user);
        snapshot.setMonth(dto.getMonth());
        snapshot.setTotalSpent(dto.getTotalSpent());
        snapshot.setServiceCount(dto.getServiceCount());
        snapshot.setMostCommonRepairType(repairType);

        return mapper.toDto(repository.save(snapshot));
    }

    @Transactional
    public AnalyticsSnapshotResponseDto update(Long id, AnalyticsSnapshotUpdateDto dto) {
        AnalyticsSnapshot snapshot = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Analytics snapshot not found"));

        snapshot.setTotalSpent(dto.getTotalSpent());
        snapshot.setServiceCount(dto.getServiceCount());

        if (dto.getMostCommonRepairTypeId() != null) {
            RepairType repairType = repairTypeRepository.findById(dto.getMostCommonRepairTypeId())
                    .orElseThrow(() -> new NoSuchElementException("Repair type not found"));
            snapshot.setMostCommonRepairType(repairType);
        }

        return mapper.toDto(repository.save(snapshot));
    }

    @Transactional
    public void delete(Long id) {
        AnalyticsSnapshot snapshot = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Analytics snapshot not found"));
        repository.delete(snapshot);
    }
}
