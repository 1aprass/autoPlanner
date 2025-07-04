package ru.neoflex.autoplanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.neoflex.autoplanner.entity.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUserId(Long userId);
    List<Document> findByVehicleId(Long vehicleId);
    List<Document> findByExpiresAtBefore(LocalDateTime now);
    Optional<Document> findByFileUrl(String url);
}
