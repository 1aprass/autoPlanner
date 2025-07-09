package ru.neoflex.autoplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.neoflex.autoplanner.entity.ServiceCenterRepairType;

import java.util.List;
import java.util.Optional;

public interface ServiceCenterRepairTypeRepository extends JpaRepository<ServiceCenterRepairType, Long> {
    List<ServiceCenterRepairType> findByServiceCenter_Id(Long serviceCenterId);
    Optional<ServiceCenterRepairType> findByServiceCenter_IdAndRepairType_Id(Long serviceCenterId, Long repairTypeId);
}