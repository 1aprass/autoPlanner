package ru.neoflex.autoplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.neoflex.autoplanner.entity.ServiceHistory;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceHistoryRepository extends JpaRepository<ServiceHistory, Long>  {
    List<ServiceHistory> findByVehicleId(Long vehicleId);
    List<ServiceHistory> findByServiceCenterId(Long centerId);
    List<ServiceHistory> findByRepairTypeId(Long repairTypeId);
    List<ServiceHistory> findByVehicleIdAndDateBetween(Long vehicleId, LocalDateTime start, LocalDateTime end);
}
