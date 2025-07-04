package ru.neoflex.autoplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.neoflex.autoplanner.entity.AppointmentRequest;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {
    List<AppointmentRequest> findByUserId(Long userId);
    List<AppointmentRequest> findByVehicleId(Long vehicleId);
    List<AppointmentRequest> findByServiceCenterId(Long serviceCenterId);
    List<AppointmentRequest> findByStatus(String status);
    List<AppointmentRequest> findByServiceDateAfter(LocalDateTime now);
}
