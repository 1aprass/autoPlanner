package ru.neoflex.autoplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.neoflex.autoplanner.entity.ServiceCenter;
import ru.neoflex.autoplanner.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Long> {
    List<ServiceCenter> findByNameContainingIgnoreCase(String name);
    List<ServiceCenter> findByRatingGreaterThanEqual(BigDecimal minRating);
    Optional<ServiceCenter> findById(Long id);
}
