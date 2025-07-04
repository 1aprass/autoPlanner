package ru.neoflex.autoplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.neoflex.autoplanner.entity.Reminder;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUserId(Long userId);
    List<Reminder> findByVehicleId(Long vehicleId);
    List<Reminder> findByIsSentFalseAndRemindDateBefore(LocalDateTime date);
}
